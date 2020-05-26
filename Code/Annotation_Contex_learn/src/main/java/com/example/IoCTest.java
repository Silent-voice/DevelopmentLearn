package com.example;

import com.example.pojo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IoCTest {
    private static Logger log = LogManager.getLogger(IoCTest.class);

    public static void main(String[] args) {
        ApplicationContext ctx
                = new AnnotationConfigApplicationContext(AppConfig.class);
        User user = ctx.getBean(User.class);
        log.info(user.getId());
    }
}
