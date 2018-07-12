package com.neo.java.reflect;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neo.java.reflect.model.Person;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用Class对象创建实例
 * http://www.cnblogs.com/pony1223/p/7445950.html
 */
@Slf4j
public class Test_02 {

    private Class  clazz;
    private Person person;

    @Before
    public void init() throws Exception {
        clazz = Class.forName("com.neo.java.reflect.model.Person");
    }

    /**使用Class对象的newInstance方法创建对象*/
    @Test
    public void test01() throws Exception {
        person = (Person) clazz.newInstance();
        log.info("person.name = {}", person.name);
    }

    /**使用公共空参构造创建对象
     *
     * getConstructor()是获取public构造方法的
     *
     * */
    @Test
    public void test02() throws Exception {
        Constructor constructor = clazz.getConstructor();
        person = (Person) constructor.newInstance();
    }

    /**使用公共带参构造创建对象*/
    @Test
    public void test03() throws Exception {
        Constructor constructor = clazz.getConstructor(String.class);
        person = (Person) constructor.newInstance("John");
    }

    /**使用公共带多个有参构造创建对象*/
    @Test
    public void test04() throws Exception {
        Constructor constructor = clazz.getConstructor(String.class, int.class);
        person = (Person) constructor.newInstance("Lenon", 38);
    }

    /**使用私有构造创建对象
     *
     * 需要使用getDeclaredConstructor()方法
     *
     * 需要先获取方法的权限
     *
     * */
    @Test
    public void test05() throws Exception {
        Constructor constructor = clazz.getDeclaredConstructor(List.class);
        constructor.setAccessible(true);
        person = (Person) constructor.newInstance(new ArrayList<>());
    }


    /**
     * getDeclaredConstructor 可以获取任意可见的构造
     * */
    @Test
    public void test06() throws Exception {
        Constructor constructor = clazz.getDeclaredConstructor(String.class);
        person = (Person) constructor.newInstance("John");
    }


    @After
    public void logOut() {
        if (person != null) {
            log.info("person.name = {}", person.name);
        } else {
            log.warn("person is null");
        }
    }

}