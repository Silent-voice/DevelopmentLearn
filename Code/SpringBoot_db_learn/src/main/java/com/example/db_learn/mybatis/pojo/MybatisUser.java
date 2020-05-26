package com.example.db_learn.mybatis.pojo;

import org.apache.ibatis.type.Alias;

@Alias(value = "user")  // MyBatis指定别名
public class MybatisUser {
    private Long id = null;

    private String userName = null;

    // 特殊类型数据在Mybatis中需要使用typeHandler进行转换
    private SexEnum sex = null;

    private String note = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
