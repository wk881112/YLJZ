package com.neo.springexamples.ioc.pojo.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class AppService {

    @Autowired
//    @Lazy
    private TestDemo testDemo;

    @Autowired
//    @Lazy
    private TestDemo testDemo2;

    public String userDemo() {
        return this.testDemo.getDemo();
    }
}
