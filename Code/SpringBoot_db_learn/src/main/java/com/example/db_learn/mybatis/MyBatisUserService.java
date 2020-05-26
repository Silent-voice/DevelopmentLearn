package com.example.db_learn.mybatis;

import com.example.db_learn.mybatis.pojo.MybatisUser;

public interface MyBatisUserService {
    public MybatisUser getUser(Long id);
}
