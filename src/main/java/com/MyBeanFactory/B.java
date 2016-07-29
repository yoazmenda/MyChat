package com.MyBeanFactory;

import com.MyBeanFactory.annotations.Component;
import com.MyBeanFactory.annotations.Inject;

@Component
public class B {

    @Inject
    private C c;

    public B() {
    }

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }


}
