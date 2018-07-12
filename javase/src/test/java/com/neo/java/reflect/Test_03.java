package com.neo.java.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.neo.java.reflect.model.Person;

import lombok.extern.slf4j.Slf4j;

/**
 * Class对象调用对象方法
 * https://www.cnblogs.com/pony1223/p/7446098.html
 */
@Slf4j
public class Test_03 {

    private Class  clazz;
    private Person person;

    @Before
    public void init() throws Exception {
        clazz = Class.forName("com.neo.java.reflect.model.Person");
        person = (Person) clazz.newInstance();
    }

    /**
     * 无参 public 方法的调用
     */
    @Test
    public void test01() throws Exception {
        Method sayHello1 = clazz.getMethod("sayHello");
        sayHello1.invoke(person, null);
    }

    /**
     * 有参 public 方法的调用
     */
    @Test
    public void test02() throws Exception {
        Method sayHello1 = clazz.getMethod("sayHello", String.class);
        sayHello1.invoke(person, "John");
    }

    /**
     * 有参 private 方法的调用
     *
     * 需要获取权限
     */
    @Test
    public void test03() throws Exception {
        Method sayHello1 = clazz.getDeclaredMethod("sayHello", List.class);
        sayHello1.setAccessible(true);
        sayHello1.invoke(person, new ArrayList<>());
    }

    /**
     * 静态方法，由于不需要对象，可以直接传 null
     * */
    @Test
    public void test04() throws Exception {
        Method sayHello = clazz.getDeclaredMethod("sayHello", Person.class);
        sayHello.invoke(null, person);
    }

}