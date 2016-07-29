package com.MyBeanFactory.exp;

/**
 * Created by yoaz on 7/29/2016.
 */
public class NoSuchBeanException extends RuntimeException{
    public NoSuchBeanException(Class<?> clazz) {
        super("No such bean exist: " + clazz.getName());
    }
}
