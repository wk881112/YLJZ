package com.neo.springexamples.ioc.pojo.annotation;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestDemo {

    @Setter
    @Getter
    private String demo;

    public TestDemo() {
        log.info("-----------new TestDemo()--------------");
        this.demo = "demo";
    }

    public TestDemo(double random) {
        log.info("-----------new TestDemo(random)--------------");
        this.demo = "demo" + random;
    }
}
