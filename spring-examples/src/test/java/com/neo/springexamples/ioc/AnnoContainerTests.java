package com.neo.springexamples.ioc;

import com.neo.springexamples.ioc.pojo.annotation.Wether;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnoContainerTests {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
            "com.neo.springexamples.ioc.pojo.annotation");
        Wether wether = (Wether)appContext.getBean("wether");
        System.out.println(wether);

    }
}
