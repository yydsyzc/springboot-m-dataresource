package org.spring.springboot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: yangzhicheng
 * @Date: 2018/4/18 21:27
 */
@Component
public class JedisPoolWrapper {

    private JedisPool jedisPool=null;

    @Autowired
    private Parameters parameters;

    public JedisPool getJedisPool(){return jedisPool;}

    @PostConstruct
    public void init(){
        try {
            JedisPoolConfig config=new JedisPoolConfig();
            config.setMaxTotal(parameters.getRedisMaxTotal());
            config.setMaxIdle(parameters.getRedisMaxIdle());
            config.setMaxWaitMillis(parameters.getRedisMaxWaitMillis());
            System.out.println(parameters.getRedisDatabase());
            jedisPool = new JedisPool(config,parameters.getRedisHost(),parameters.getRedisPort(),100000,null,parameters.getRedisDatabase());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
