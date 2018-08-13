package com.neo.springexamples.ioc.pojo.xml;

public class ProcessorDemoBean {

    private String propertyOne;

    public String getPropertyOne() {
        return propertyOne;
    }

    public void setPropertyOne(String propertyOne) {
        this.propertyOne = propertyOne;
    }

    private void doInit() {
        System.out.println("执行初始化程序");
    }

    @Override
    public String toString() {
        return "ProcessorDemoBean{" +
                "propertyOne='" + propertyOne + '\'' +
                '}';
    }
}
