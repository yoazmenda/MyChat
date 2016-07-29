package com.MyBeanFactory;

import com.MyBeanFactory.annotations.Component;
import com.MyBeanFactory.annotations.Inject;

@Component
public class C {

    @Inject
    private D d;

    public C() {
    }

    public void setD(D d) {
        this.d = d;
    }

    public D getD() {
        return d;
    }


}
