package com.example.db_learn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@SpringBootApplication
// 定义 JPA 接口扫描包路径
@EnableJpaRepositories(basePackages = "com.example.db_learn.jpa")
// 定义实体 Bean 扫描包路径
@EntityScan(basePackages = "com.example.db_learn.jpa.pojo")

//Mybatis配置
@MapperScan(
        basePackages = "com.example.db_learn.mybatis.*",  //指定扫描包
        sqlSessionFactoryRef = "sqlSessionFactory",      //指定sqlSessionFactory
        sqlSessionTemplateRef = "sqlSessionTemplate",   //指定Template替代SessionFactory
        annotationClass = Repository.class
)


public class DbLearnApplication {

    @Autowired
    private RedisTemplate redisTemplate = null;

    @PostConstruct      // 服务器加载Servlet的时候自动执行，只会执行一次，执行顺序：Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
    public void init(){

    }

    // 设置RedisTemplate的序列化器
    private void initRedisTemplate(){
        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
    }




    public static void main(String[] args) {
        SpringApplication.run(DbLearnApplication.class, args);
    }

}
