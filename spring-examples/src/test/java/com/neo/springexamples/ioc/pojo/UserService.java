package com.neo.springexamples.ioc.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class UserService {

    @Autowired
    public ApplicationContext applicationContext;

    public String getDemoStr() {
        return demoStr;
    }

    //    @Required
    public void setDemoStr(String demoStr) {
        this.demoStr = demoStr;
    }

    private String demoStr;

    public void saveUser() {
        System.out.println("save user");
    }

    public void testApplicationContext() {
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.saveUser();
    }

    public UserService() {
    }

    public UserService(String demoStr) {
        this.demoStr = demoStr;
    }

    public void destroy() {
        System.out.println(this.getClass().getName() + " destroyed");
    }

}
