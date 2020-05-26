package com.example.baselearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// 定义 JPA 接口扫描包路径
@EnableJpaRepositories(basePackages = "com.example.baselearn.db")
// 定义实体 Bean 扫描包路径
@EntityScan(basePackages = "com.example.baselearn.pojo")

public class BaselearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaselearnApplication.class, args);
    }

}
