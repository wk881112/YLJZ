package com.neo.java.reflect;

import java.lang.reflect.Proxy;

import org.junit.Test;

import com.neo.java.reflect.model.Hello;
import com.neo.java.reflect.model.HelloImpl;
import com.neo.java.reflect.model.MyInvocationHandlerImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * java.lang.reflect
 *
 * https://docs.oracle.com/javase/tutorial/reflect/index.html
 */
@Slf4j
public class Test_06 {

    /**
     * 使用jdkProxy代理
     * */
    @Test
    public void test01() {

        HelloImpl hello = new HelloImpl();
        MyInvocationHandlerImpl handler = new MyInvocationHandlerImpl(hello);

        // 构造代码实例
        Hello proxyHello = (Hello) Proxy.newProxyInstance(hello.getClass().getClassLoader(),
            hello.getClass().getInterfaces(), handler);

        // 调用代理方法
        proxyHello.sayHello();
    }

}
