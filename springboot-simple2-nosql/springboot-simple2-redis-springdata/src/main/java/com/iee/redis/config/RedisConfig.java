package com.iee.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
@EnableCaching  //继承CachingConfigurerSupport并重写方法，配合该注解实现spring缓存框架的使用
@EnableConfigurationProperties(RedisProperties.class)//加入spring data redis的redis配置
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisProperties redisProperties;

    /**
     * 配置application.yaml下spring.redis.jedis.pool参数,如max-idle等
     **/
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        RedisProperties.Pool pool = redisProperties.getJedis().getPool();
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        if (pool.getMaxWait() != null) {
            config.setMaxWaitMillis(pool.getMaxWait().toMillis());
        }
        return config;
    }

    /**
     * application.yaml关闭redis的自动配置功能，需要手动创建bean
     */
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory = null;
        String password = redisProperties.getPassword();
//		 集群模式或者单机模式都行
        String[] serverArray = redisProperties.getHost().split(",");
        if (serverArray.length == 1) {//单机模式
            String[] ipPortPair = serverArray[0].split(":");
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(ipPortPair[0]);
            redisStandaloneConfiguration.setPort(Integer.valueOf(ipPortPair[1]));
            redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
            redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());
            jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        } else if (serverArray.length % 2 == 0) {//集群模式
            RedisClusterConfiguration clusterConfig = new
                    RedisClusterConfiguration(Arrays.asList(serverArray));
//            if(!StringUtils.isEmpty(redisProperties.getPassword())){
            clusterConfig.setPassword(RedisPassword.of(password));
//            }
            jedisConnectionFactory = new JedisConnectionFactory(clusterConfig, jedisPoolConfig);
        } else {//错误的节点数量
            throw new RuntimeException("错误的节点数量,无法创建连接");
        }
        //设置密码
        return jedisConnectionFactory;

    }

    /**
     * 查看RedisAutoConfiguration类，自定义 StringRedisTemplate ，指定序列化和反序列化的处理类 *
     * 通过源码可以看出，SpringBoot自动帮我们在容器中生成了一个RedisTemplate和一个StringRedisTemplate。但是，这个RedisTemplate的泛型是<Object,Object>，写代码不方便，需要写好多类型转换的代码；我们需要一个泛型为<String,Object>形式的RedisTemplate。并且，这个RedisTemplate没有设置数据存在Redis时，key及value的序列化方式。
     * 看到这个@ConditionalOnMissingBean注解后，就知道如果Spring容器中有了RedisTemplate对象了，这个自动配置的RedisTemplate不会实例化。因此我们可以直接自己写个配置类，配置RedisTemplate。
     *
     * @Primary 在autoware的时候优先选择我注册的对象，而不是springboot的StringRedisTemplate
     */
    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // key采用String的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // value序列化方式采用jackson
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 设置springcache的缓存管理器
     */
    @Bean
//    @Override
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager redisCacheManager = RedisCacheManager.create(redisConnectionFactory);
        //设置缓存过期时间
//        redisCacheManager.setDefaultExpiration(60);//秒
        return redisCacheManager;
    }

    /**
     * 设置springcache的重写缓存key生成策略，可根据自身业务需要进行自己的配置生成条件
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }


}
