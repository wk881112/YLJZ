package com.neo.springexamples.ioc.context;

import com.google.gson.Gson;
import com.neo.springexamples.ioc.context.pojo.annotation.AppService;
import com.neo.springexamples.ioc.context.pojo.annotation.Wether;
import org.junit.Assert;
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

    @Test
    public void test02() {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
                "com.neo.springexamples.ioc.pojo.annotation");
        // 如果使用注入的 sigleton
        AppService appService = (AppService)appContext.getBean("appService");
        System.out.println(new Gson().toJson(appService));
        Assert.assertEquals("demo", appService.userDemo());

    }
}
