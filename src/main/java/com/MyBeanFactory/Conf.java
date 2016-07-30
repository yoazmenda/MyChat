package com.MyBeanFactory;

import com.MyBeanFactory.D;
import com.MyBeanFactory.annotations.Bean;
import com.MyBeanFactory.annotations.Configuration;

@Configuration
public class Conf {

    @Bean
    public D createCustomDBean(){
        return new D("yoaz",123);
    }

}
