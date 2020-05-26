package com.example.db_learn.mybatis;

import com.example.db_learn.mybatis.dao.MybatisUserDao;
import com.example.db_learn.mybatis.pojo.MybatisUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyBatisUserServiceImpl implements MyBatisUserService{

    @Autowired
    private MybatisUserDao mybatisUserDao = null;

    @Override
    public MybatisUser getUser(Long id) {
        return mybatisUserDao.getUser(id);
    }
}
