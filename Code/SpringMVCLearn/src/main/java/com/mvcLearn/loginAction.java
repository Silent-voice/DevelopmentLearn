package com.mvcLearn;

import com.mvcLearn.pojo.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Controller
public class loginAction {
    private String lFailView = "loginFail";
    private String lSuccessView = "loginSuccess";

    @PostMapping("doLogin")
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        //将表单的参数封装到command中
        LoginForm lf = (LoginForm)command;
        if (lf.getAccount().equals("111111") && lf.getPassword().equals("123456")){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("LoginForm", lf);
            List<String> msgList = new LinkedList<String>();
            msgList.add("你好，Spring MVC");
            map.put("msg", msgList);
            System.out.println("登录成功");

            //返回视图和模型
            return new ModelAndView(this.lSuccessView, map);
        }else{
            // 只返回模型
            return new ModelAndView(this.lFailView);
        }
    }
}
