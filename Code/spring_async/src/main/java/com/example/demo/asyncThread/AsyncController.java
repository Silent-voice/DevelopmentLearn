package com.example.demo.asyncThread;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/asnyc")
public class AsyncController {

    @Autowired
    private AsyncService asyncService = null;

    @RequestMapping("/page")
    @ResponseBody
    public String asnycPage() throws InterruptedException{
        System.out.println("请求线程名称：[" + Thread.currentThread().getName() + "]");
        asyncService.generateReport();
        return "asnyc";
    }

}
