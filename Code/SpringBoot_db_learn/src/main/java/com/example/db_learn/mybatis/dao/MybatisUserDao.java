package com.example.db_learn.mybatis.dao;

import com.example.db_learn.mybatis.pojo.MybatisUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository //DAO Bean标识
public interface MybatisUserDao {
    public MybatisUser getUser(Long id);
}
