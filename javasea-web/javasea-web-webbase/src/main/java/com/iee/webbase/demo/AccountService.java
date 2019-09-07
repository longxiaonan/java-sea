package com.iee.webbase.demo;

/**
 * 账户服务
 *
 * @author dadiyang
 * @since 2019-06-02
 */
public interface AccountService {
    /**
     * 通过id查询账户
     *
     * @param id 主键id
     * @return 账户实体
     */
    Account getById(int id);

    /**
     * 插入账户记录并返回新生成的主键
     *
     * @param account 账户实体
     * @return 主键
     */
    int insert(Account account);
}
