package com.MyBeanFactory;

import com.MyBeanFactory.annotations.Component;
import com.MyBeanFactory.annotations.Inject;

@Component
public class A {

    @Inject
    private B b;

    @Inject
    private C c;

    @Inject
    private D d;


    public A() {
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }
}
