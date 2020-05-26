package com.mvcLearn;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ToLoginAction {

    @RequestMapping("toLogin")
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return new ModelAndView("login");
    }

}
