package com.iee.orm.mybatis.common;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.iee.orm.mybatis.common.UserHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实现 拦截select语句，实现尾部拼接sql来查询本租户和子租户信息
 * @author longxiaonan@aliyun.com
 */
@Slf4j
@Configuration
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class SqlInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        // 非select语句或者是存储过程 则跳过)
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedstatement");
        if (SqlCommandType.SELECT != mappedStatement.getSqlCommandType()
                || StatementType.CALLABLE == mappedStatement.getStatementType()) {
            return invocation.proceed();
        }
        // 拼接sql执行
        String targetSql = getSqlByInvocation(metaObject, invocation);
        //重新设置sql
        metaObject.setValue(PluginUtils.DELEGATE_BOUNDSQL_SQL, targetSql);
        return invocation.proceed();
    }

    /**
     * 拼接sql执行
     * @param metaObject
     * @param invocation
     * @return
     */
    private String getSqlByInvocation(MetaObject metaObject, Invocation invocation) throws NoSuchFieldException, IllegalAccessException {
        //在原始的sql中拼装sql，方式一
        String originalSql = (String) metaObject.getValue(PluginUtils.DELEGATE_BOUNDSQL_SQL);
        metaObject.setValue(PluginUtils.DELEGATE_BOUNDSQL_SQL, originalSql);
        String targetSql = addDataSql(originalSql);
        return targetSql;

        //在原始的sql中拼装sql，方式二
//        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
//        BoundSql boundSql = statementHandler.getBoundSql();
//        String sql = boundSql.getSql();
//        Field field = boundSql.getClass().getDeclaredField("sql");
//        field.setAccessible(true);
//        field.set(boundSql, addDataSql(sql));
//        return sql;
    }

    /**
     * 将原始sql进行拼接
     * @param sql
     * @return
     */
    static String addDataSql(String sql) {
        //需要去掉“;" 因为“;"表示sql结束，不去掉那么后面的拼接会受到影响
        sql = StringUtils.replace(sql, ";", "");
        StringBuilder sb = new StringBuilder(sql);
        String tenantId = UserHelper.getTenantId();

        //用于查看子租户信息的sql后缀
        String suffSql = " `tenant_id` IN " +
                "(SELECT " +
                "tt.`tenant_id` " +
                "FROM " +
                "t_tenant tt " +
                "WHERE " +
                "(SELECT " +
                "INSTR(tt.path," + tenantId + "))) ";

        String regex = "(.*)(where)(.*)";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(sql);
        if (matcher.find()) {
            String whereLastSql = matcher.group(matcher.groupCount());
            //where 的条件存在 且是括号对的情况下，是不能再加“where”的, 但是需要添加“and”
            int left = StringUtils.countMatches(whereLastSql, "(");
            int right = StringUtils.countMatches(whereLastSql, ")");
            if(left == right){
                sb.append(" and ");
                sb.append(suffSql);
                log.info("数据权限替换后sql:--->" + sb.toString());
                return sb.toString();
            }
        }
        //其他情况下需要添加where
        sb.append(" where ");
        sb.append(suffSql);
        log.info("数据权限替换后sql:--->" + sb.toString());
        return sb.toString();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, new SqlInterceptor());
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
