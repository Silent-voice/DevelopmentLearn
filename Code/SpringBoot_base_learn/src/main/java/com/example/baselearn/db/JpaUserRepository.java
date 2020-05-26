package com.example.baselearn.db;

import com.example.baselearn.pojo.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

// 不需要提供实现类，Spring系统会根据JPA接口规范自动帮我们实现相应方法
public interface JpaUserRepository extends JpaRepository<JpaUser, Long> {
}
