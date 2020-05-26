package com.example.db_learn.jpa.pojo;

import javax.persistence.*;

@Entity(name = "japUser")
@Table(name = "t_user")
public class JpaUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(name = "user_name")
    private String userName = null;

    @Convert(converter = SexConverter.class)
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
