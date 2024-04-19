package org.csu.mypetstore.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    //addPath or excludePath using * : https://blog.csdn.net/a704397849/article/details/100542789
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginInterceptor).addPathPatterns("/order/**","/cart/**","/account/info").excludePathPatterns("/catalog/**");
    }

//    @Bean
//    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor(){
//        return new OptimisticLockerInnerInterceptor();
//    }
}
