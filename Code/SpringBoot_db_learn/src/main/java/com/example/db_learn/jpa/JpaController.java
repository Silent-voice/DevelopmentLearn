package com.example.db_learn.jpa;

import com.example.db_learn.jpa.pojo.JpaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/jpa")
public class JpaController {
    @Autowired
    private JpaRepository jpaUserRepository = null;

    @RequestMapping("/getUser")
    @ResponseBody
    public JpaUser getUser(Long id){
        JpaUser user = (JpaUser) jpaUserRepository.findById(id).get();
        return user;
    }


}
