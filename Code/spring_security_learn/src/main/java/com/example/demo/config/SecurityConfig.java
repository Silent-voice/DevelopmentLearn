package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/*
数据库
角色表

CREATE TABLE t_role(
	id INT UNSIGNED AUTO_INCREMENT,
	role_name VARCHAR(60) NOT NULL,
	note VARCHAR(256) NOT NULL,
	PRIMARY KEY (id)
);
INSERT INTO t_role (role_name, note) VALUES ('ROLE_ADMIN', 'admin');
INSERT INTO t_role (role_name, note) VALUES ('ROLE_USER', 'user');

用户表
CREATE TABLE t_user(
	id INT UNSIGNED AUTO_INCREMENT,
	user_name VARCHAR(60) NOT NULL,
	pwd VARCHAR(100) NOT NULL,
	available INT(1) DEFAULT 1 CHECK(available IN (0, 1)),
	note VARCHAR(256) ,
	PRIMARY KEY (id) ,
	UNIQUE(user_name)
);

// 密码 'abc'
INSERT INTO t_user (user_name, pwd, note) VALUES ('admin_1', '$2a$10$km28r4yGchefUgmKU1Iym.hSjZN.ju5eum6kFm5EjsZk5hP27EGf2', 'admin 1');
// 密码 '123456'
INSERT INTO t_user (user_name, pwd, note) VALUES ('user_1', '$2a$10$38aUeIXuFPqMjoGGuRjmAO/jWTiHS5MQS2f1cai8xEbFH38FAzxlO', 'user 1');

用户角色表
CREATE TABLE t_user_role(
	id INT UNSIGNED AUTO_INCREMENT,
	role_id INT UNSIGNED NOT NULL,
	user_id INT UNSIGNED NOT NULL,
	PRIMARY KEY (id) ,
	UNIQUE(role_id, user_id)
);
INSERT INTO t_user_role (role_id, user_id) VALUES (1, 1);
INSERT INTO t_user_role (role_id, user_id) VALUES (2, 2);

外键约束
alter table t_user_role add constraint FK_Reference_1 foreign key (role_id) references t_role (id) on delete restrict on update restrict;

alter table t_user_role add constraint FK_Reference_2 foreign key (user_id) references t_user (id) on delete restrict on update restrict ;
 */




@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource = null;

    /*
    * 定义用户信息，两种方法：内存签名服务、数据库用户认证服务
    * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        // 密码编码器，用于密码加密
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        /*
        * 内存签名服务，系统启动时将用户信息存储至内存
        * */
        /*
        auth.inMemoryAuthentication()
                // 设置密码编码器
                .passwordEncoder(passwordEncoder)
                // 注册用户 admin ，密码 abc，并赋予USER和ADMIN角色权限
                .withUser("admin")
                    .password(passwordEncoder.encode("abc"))
                    .roles("USER", "ADMIN")     // Spring Security会自动加前缀ROLE_
                // 连接方法
                .and()
                .withUser("user1")
                    .password(passwordEncoder.encode("123456"))
                    .roles("USER")
        ;
        */

        /*
        // 为了取消 and()，也可以这样写
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> userConfig = auth.inMemoryAuthentication().passwordEncoder(passwordEncoder);
        userConfig.withUser("admin")
                .password(passwordEncoder.encode("abc"))
                .authorities("ROLE_USER", "ROLE_ADMIN");
        userConfig.withUser("user1")
                .password(passwordEncoder.encode("123456"))
                .roles("ROLE_USER");
        */

        /*
         * 数据库用户认证服务，用户信息存储至数据库表中，验证登录时通过SQL语句进行查询验证
         *
         * 1. 数据库中存储的密码也是通过PasswordEncoder加密后的，而不是密码原文
         * */
        String pwdQuery = "SELECT user_name, pwd, available FROM t_user WHERE user_name = ?";
        String roleQuery = "SELECT u.user_name, r.role_name FROM t_user u, t_user_role ur, t_role r " +
                "WHERE u.id = ur.user_id and r.id = ur.role_id and u.user_name = ?";
        auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder)
                // 数据源
                .dataSource(dataSource)
                // 查询用户，自动判断密码是否一致
                .usersByUsernameQuery(pwdQuery)
                // 查询并赋予权限
                .authoritiesByUsernameQuery(roleQuery);

    }



    /*
     * 定义各页面的访问权限
     *
     * authorizeRequests()：请求权限匹配器
     * 指定请求
     *      anyRequest()：所有请求
     *      antMatchers()：指定URL的请求
     *
     * 指定请求权限
     *      permitAll()：所有情况都允许访问
     *      authenticated()：签名认证成功就允许访问
     *      hasRole()/hasAnyRole()：拥有某种权限才允许访问
     *      access()：使用表达式来来匹配多种权限要求
     *
     * requiresChannel()：通道设置匹配器
     * 指定请求和上述方法一样
     * 通道设置
     *      requiresSecure()：HTTPS通道
     *      requiresInsecure()：HTTP通道
     *
     *
     * rememberMe()：一次登录，后面在有效期内免登录
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                // 设置用户访问权限
                .authorizeRequests()
                    .antMatchers( "/test1" ).permitAll()
                    .antMatchers( "/test2").hasRole("ADMIN")
                    .antMatchers( "/index").hasAnyRole("USER","ADMIN")
                    .antMatchers( "/test3").hasAuthority("ROLE_ADMIN")
                    .antMatchers( "/test4").access("hasRole('ADMIN') and hasRole('DBA')")
                    .antMatchers( "/login/page" ).permitAll()
                    .anyRequest().authenticated()  //其他请求验证成功就运行访问
                .and()

                // 设置HTTP通道，HTTPS或者HTTP
                .requiresChannel()
                    .antMatchers("/test1").requiresSecure()
                    .antMatchers("/test2").requiresInsecure()
                .and()

                .rememberMe()
                    .tokenValiditySeconds(86400)    // 有效期
                    .key("remember_me_key")         // 存储于cookies中的key名，或自动加密
                .and()


                .formLogin()    //使用默认登录页面
                    // 指定登录页面，注意指定页面访问权限要设为.permitAll()，否则会死循环
                    // 验证得先登录，但是登录页面访问又得先验证，就造成跳转次数过多的死循环
                    .loginPage("/login/page")
                    // 设置对登录信息进行处理的URL
                    // login里的form提交地址action要和这里定义的相同
                    .loginProcessingUrl("/authentication/form")
                    .defaultSuccessUrl("/index")
                .and()

                .logout()
                    .logoutUrl("/lgout/page")
                    .logoutSuccessUrl("/index")
                .and()
                .httpBasic();   //启动Http Basic验证

        http.csrf().disable();

    }

}
