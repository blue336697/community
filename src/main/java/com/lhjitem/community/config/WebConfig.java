package com.lhjitem.community.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @auther lhj
 * @date 2022/1/29 15:57
 */

@Configuration
//如果此时CSS样式被拦截了就可以吧下面这个注解删掉，也可将静态资源不过滤
//这个注解就是将下面这个配置类作为自定义类来代替自动配置类，而自动配置类是由静态资源的寻找的，但是被替代成我们自定义的以后，我们并没有
//制定静态资源的寻找方式，所以这时候姿态资源就会失效
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionHandler sessionHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionHandler).addPathPatterns("/**");
    }
}
