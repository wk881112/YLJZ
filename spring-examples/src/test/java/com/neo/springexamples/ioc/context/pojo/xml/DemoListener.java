package com.neo.springexamples.ioc.context.pojo.xml;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

public class DemoListener implements ApplicationListener<ApplicationContextEvent>, DisposableBean,
                          InitializingBean, ApplicationContextAware,
                          DestructionAwareBeanPostProcessor {

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        System.out.println("=========== onApplicationEvent ============ " + event.toString());
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("========== destroy ============= ");
    }

    public void customDestroy() {
        System.out.println("========== customDestroy ============= ");
    }

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        System.out.println("========== postProcessBeforeDestruction ============= ");
    }

    @Override
    public boolean requiresDestruction(Object bean) {
        return false;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean,
                                                  String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean,
                                                 String beanName) throws BeansException {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("========== afterPropertiesSet ============= ");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("========== setApplicationContext ============= ");
    }
}
