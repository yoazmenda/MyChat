package com.company;

import com.company.annotations.Component;
import com.company.annotations.Inject;

@Component
public class A{

    @Inject
    private B b;

}