package com.neo.springexamples.ioc.pojo;

public class UserService {

    public String getDemoStr() {
        return demoStr;
    }

    //    @Required
    public void setDemoStr(String demoStr) {
        this.demoStr = demoStr;
    }

    private String demoStr;

    public void saveUser() {
    }

    public UserService() {
    }

    public UserService(String demoStr) {
        this.demoStr = demoStr;
    }
}
