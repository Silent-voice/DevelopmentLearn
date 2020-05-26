package com.example.db_learn.mybatis;

import com.example.db_learn.mybatis.pojo.MybatisUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mybatis")
public class MyBatisController {

    @Autowired  //实际获取的是MyBatisUserServiceImpl对象实例，只有他注册到了IoC容器中
    private MyBatisUserService myBatisUserService = null;

    @RequestMapping("/getUser")
    @ResponseBody
    public MybatisUser getUser(Long id){
        return myBatisUserService.getUser(id);
    }


}
