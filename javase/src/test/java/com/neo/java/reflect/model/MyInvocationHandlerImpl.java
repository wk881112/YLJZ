package com.neo.java.reflect.model;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandlerImpl implements InvocationHandler {

    private Object target;

    public MyInvocationHandlerImpl(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass().getName());
        System.out.println("Invoking sayHello");
        Object result = method.invoke(target, args);
        return result;
    }
}
