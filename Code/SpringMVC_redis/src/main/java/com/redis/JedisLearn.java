package com.redis;

import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

public class JedisLearn {

    public void baseAction(){
        // 构造jedis对象
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        // 类似命令行的操作，方法名与命令名相同
        jedis.set("name", "鸟鹏");
        String value = jedis.get("name");

        System.out.println(value);

        // 关闭连接
        jedis.close();

    }

    // 连接池
    public void JedisPoolDemo(){
        // 构建连接池配置信息
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 设置最大连接数
        jedisPoolConfig.setMaxTotal(50);
        // 构建连接池
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);

        // 从连接池中获取连接
        Jedis jedis = jedisPool.getResource();
        // 读取数据
        System.out.println(jedis.get("name"));


        // 将连接还回到连接池中
        // 旧写法：jedisPool.returnResource(jedis);
        jedis.close();

        // 释放连接池
        jedisPool.close();
    }

    // 集群
    public void ShardedJedisPoolDemo(){
        // 构建连接池配置信息
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 设置最大连接数
        poolConfig.setMaxTotal(50);

        // 定义集群信息
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        // 添加集群信息
        shards.add(new JedisShardInfo("127.0.0.1", 6379));
        //shards.add(new JedisShardInfo("192.168.48.22", 6379));

        // 定义集群连接池
        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(poolConfig, shards);
        ShardedJedis shardedJedis = null;
        try {
            // 从连接池中获取到jedis分片对象
            shardedJedis = shardedJedisPool.getResource();
            // 从redis中获取数据
            String value = shardedJedis.get("name");
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (shardedJedis != null) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
        }
        // 关闭连接池
        shardedJedisPool.close();
    }


}




