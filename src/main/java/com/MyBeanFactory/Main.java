package com.MyBeanFactory;

import com.MyBeanFactory.BeanFactory.ApplicationContext;
import com.MyBeanFactory.annotations.Component;
import com.MyBeanFactory.annotations.Inject;

@Component
public class Main {

    @Inject
    private static A a;

    public static void main(String[] args){
        ApplicationContext ctx = new ApplicationContext("com.MyBeanFactory");
        int beanCount = ctx.getBeanCount();
        System.out.println(beanCount);
        a.getD().makeSound();

    }

}
