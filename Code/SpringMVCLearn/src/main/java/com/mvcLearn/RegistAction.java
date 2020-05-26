package com.mvcLearn;


import com.mvcLearn.pojo.RegistForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Controller
public class RegistAction {
    private String rFailView = "registFail";
    private String rSuccessView = "registSuccess";

    @PostMapping("doRegist")
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response, Object command, BindException errors)
            throws Exception {

        RegistForm rf = (RegistForm)command;
        if (rf.getAccount().equals("111111") && rf.getPassFirst().equals("123456")
                &&rf.getPassSecond().equals("123456")){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("LoginForm", rf);
            List<String> msgList = new LinkedList<String>();
            msgList.add("你好，Spring MVC");
            map.put("msg", msgList);
            //返回视图和模型，map为模型
            return new ModelAndView(this.rSuccessView, map);
        }else{
            return new ModelAndView(this.rFailView);
        }
    }
}
