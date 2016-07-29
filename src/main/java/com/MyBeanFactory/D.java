package com.MyBeanFactory;

import com.MyBeanFactory.annotations.Component;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.swing.plaf.synth.SynthOptionPaneUI;

@Component
public class D {

    private String name;

    private int number;


    public D(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public D() {
        name = "John";
        number = 0;
    }

    public void makeSound(){
        System.out.println("My Name is : "  + name);
        System.out.println("My number is : "  + number);
    }
}
