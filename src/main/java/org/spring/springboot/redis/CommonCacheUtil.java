package org.spring.springboot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: yangzhicheng
 * @Date: 2018/4/18 21:39
 */
@Component
public class CommonCacheUtil {

    @Autowired
    private JedisPoolWrapper jedisPoolWrapper;

    /**
     * 缓存 可以value 永久
     * @param key
     * @param value
     */
    public void cache(String key, String value) {
        try {
            JedisPool pool = jedisPoolWrapper.getJedisPool();
            if (pool != null) {
                try (Jedis Jedis = pool.getResource()) {
                    Jedis.select(0);
                    Jedis.set(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取缓存key
     * @param key
     * @return
     */
    public String getCacheValue(String key) {
        String value = null;
        try {
            JedisPool pool = jedisPoolWrapper.getJedisPool();
            if (pool != null) {
                try (Jedis Jedis = pool.getResource()) {
                    Jedis.select(0);
                    value = Jedis.get(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 设置key value 以及过期时间
     * @param key
     * @param value
     * @param expiry
     * @return
     */
    public long cacheNxExpire(String key, String value, int expiry) {
        long result = 0;
        try {
            JedisPool pool = jedisPoolWrapper.getJedisPool();
            if (pool != null) {
                try (Jedis jedis = pool.getResource()) {
                    jedis.select(0);
                    result = jedis.setnx(key, value);
                    jedis.expire(key, expiry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 删除缓存key
     * @param key
     */
    public void delKey(String key) {
        JedisPool pool = jedisPoolWrapper.getJedisPool();
        if (pool != null) {

            try (Jedis jedis = pool.getResource()) {
                jedis.select(0);
                try {
                    jedis.del(key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置list
     * @param key
     * @param list
     * @param <T>
     */
    public <T> int setList(String key ,List<T> list){
        JedisPool pool = jedisPoolWrapper.getJedisPool();
        if (pool != null) {

            try (Jedis jedis = pool.getResource()) {
                jedis.set(key.getBytes(), SerializeUtil.serialize(list));
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 获取list
     * @param key
     * @param <T>
     * @return
     */
    public <T> List<T> getList(String key){
        JedisPool pool = jedisPoolWrapper.getJedisPool();
        if(pool.getResource() == null || !pool.getResource().exists(key.getBytes())){
            return null;
        }
        byte[] in = pool.getResource().get(key.getBytes());
        List<T> list = (List<T>) SerializeUtil.unserialize(in);
        return list;
    }


    // 存储对象
    public void setObject(String key, Object obj) {
        JedisPool pool = jedisPoolWrapper.getJedisPool();
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key.getBytes(), SerializeUtil.serialize(obj));
        } catch (Exception e) {
//            logger.info("缓存服务器连接异常！");
            e.printStackTrace();
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }


    // 获取对象
    public Object getObject(String key) {
        JedisPool pool = jedisPoolWrapper.getJedisPool();
        byte[] obj = null;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            obj = jedis.get(key.getBytes());
        } catch (Exception e) {
//            logger.info("缓存服务器连接异常！");
            e.printStackTrace();
        } finally {
            // 返还到连接池
            jedis.close();
        }
        return SerializeUtil.unserialize(obj);
    }

    /**
     * 存MAP到redis
     * @param key
     * @param map
     * @param <T>
     */
    public <T> void setMap(String key ,Map<String,T> map){
        JedisPool pool = jedisPoolWrapper.getJedisPool();
        try {
            pool.getResource().set(key.getBytes(),SerializeUtil.serialize(map));
        } catch (Exception e) {
//            logger.error("Set key error : "+e);
            e.printStackTrace();
        }
    }

    /**
     * 取redis里的map
     * @param key
     * @return
     */
    public Map<String,Object> getMap(String key){
        JedisPool pool = jedisPoolWrapper.getJedisPool();
        if(pool.getResource() == null || !pool.getResource().exists(key.getBytes())){
            return null;
        }
        byte[] in = pool.getResource().get(key.getBytes());
        Map<String,Object> map = (Map<String,Object>) SerializeUtil.unserialize(in);
        return map;
    }






}
