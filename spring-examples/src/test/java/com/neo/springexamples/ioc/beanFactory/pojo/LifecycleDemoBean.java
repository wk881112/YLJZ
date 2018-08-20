package com.neo.springexamples.ioc.beanFactory.pojo;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

public class LifecycleDemoBean implements BeanNameAware, ResourceLoaderAware, InitializingBean {

    String         demoProperty;
    String         beanName;
    ResourceLoader resourceLoader;

    public void setDemoProperty(String demoProperty) {
        this.demoProperty = demoProperty;
    }

    @Override
    public void setBeanName(String name) {
        printPoint("setBeanName");
        this.beanName = name;
    }

    private void printPoint(String methodName) {
        System.out.println("------------Invoking " + methodName + " ---  " + this.toString());
    }

    @Override
    public String toString() {
        return "LifecycleDemoBean{" + "demoProperty='" + demoProperty + '\'' + ", beanName='"
               + beanName + '\'' + ", resourceLoader=" + resourceLoader + '}';
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        printPoint("setResourceLoader");
        this.resourceLoader = resourceLoader;
    }

    public void init() {
        printPoint("init");
    }

    public void destroy() {
        printPoint("destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        printPoint("afterPropertiesSet");
    }
}
