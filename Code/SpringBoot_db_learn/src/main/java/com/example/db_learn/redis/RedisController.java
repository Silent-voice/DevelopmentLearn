package com.example.db_learn.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate = null;
    @Autowired
    private StringRedisTemplate stringRedisTemplate = null;     //使用String序列化器

    @RequestMapping("/stringAndHash")
    @ResponseBody
    public Map<String, Object> testStringAndHash(){
        // 使用的是JDK序列化器，Redis数据库中保存的是对象序列化后的字符串，无法进行计算
        redisTemplate.opsForValue().set("key1", "value1");
        redisTemplate.opsForValue().set("int_key", "q");

        // 使用String序列化器，可以进行运算
        stringRedisTemplate.opsForValue().set("int","1");
        stringRedisTemplate.opsForValue().increment("int", 1);

        //获取底层Jedis连接，进行RedisTemplate不支持的减1操作
//        Jedis jedis = (Jedis) stringRedisTemplate.getConnectionFactory().getConnection().getNativeConnection();
//        jedis.decr("int");

        Map<String, String> hash = new HashMap<String, String>();
        hash.put("field1", "value1");
        hash.put("field2", "value2");

        stringRedisTemplate.opsForHash().putAll("hash_key", hash);
        stringRedisTemplate.opsForHash().put("hash_key", "field3", "value3");


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;



    }


    @RequestMapping("/multi")
    @ResponseBody
    // redis中的事务使用
    public Map<String, Object> testMulti(){
        redisTemplate.opsForValue().set("key1", "value1");

        // 事务
        SessionCallback sessionCallback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                // 设置监控key1
                operations.watch("key1");
                // 开启事务，在exec命令执行前，所有命令只是进入队列
                operations.multi();
                operations.opsForValue().set("key2", "value2");
                // operations.opsForValue ().increment("key1", 1);      // 修改被监控key会导致事务终止
                //获取值将为 null 因为redis只是把命令放入队列
                Object value2 = operations.opsForValue().get("key2");
                System.out.println("命令在队列，所以value null <" + value2 + ">");

                //执行exec命令，将先判别 key1 是否在监控后被修改过，如果是则不执行事务，否则就执行事务
                return operations.exec();
            }
        };
        List list = (List) redisTemplate.execute(sessionCallback);

        System.out.println(list);
        Map<String, Object> map = new HashMap<String, Object>() ;
        map.put("success", true);
        return map;

    }

    @RequestMapping("/pipeline")
    @ResponseBody
    // redis使用队列
    public Map<String, Object> testPipeline(){
        Long start= System.currentTimeMillis() ;

        SessionCallback sessionCallback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (int i=1; i <= 100 ; i++) {
                    operations.opsForValue().set("pipeline_" + i, "value_" + i);
                    String value = (String) operations.opsForValue().get("pipeline_" + i);
                    if (i == 100) {
                        System.out.println("命令只是进入队列，所以值为空【" + value + ">");
                    }
                }
                return null;
            }
        };
        List list= (List)redisTemplate.executePipelined(sessionCallback);
        Long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "毫秒。");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

}
