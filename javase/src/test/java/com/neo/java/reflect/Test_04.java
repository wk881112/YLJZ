package com.neo.java.reflect;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import com.neo.java.reflect.model.Person;

import lombok.extern.slf4j.Slf4j;

/**
 * 通过反射获取属性
 * http://www.cnblogs.com/pony1223/p/7446185.html
 */
@Slf4j
public class Test_04 {

    private Class  clazz;
    private Person person;

    @Before
    public void init() throws Exception {
        clazz = Class.forName("com.neo.java.reflect.model.Person");
        person = (Person) clazz.newInstance();
    }

    /**
     * 获取 public字段
     * */
    @Test
    public void test01() throws Exception {
        Field nameField = clazz.getField("name");
        Object o = nameField.get(person);
        if (o.getClass().equals(String.class)) {
            log.info("filed name = {}, {}", o, o.getClass());
        }
    }

    /**
     * 获取private字段, 基本数据类型 ==> 包装类型
     * */
    @Test
    public void test02() throws Exception {
        Field field = clazz.getDeclaredField("age");
        field.setAccessible(true);
        Object o = field.get(person);
        if (o.getClass().equals(int.class)) {
        } else {
            log.warn("field class is {}", o.getClass());
        }
        log.info("filed age = {}, {}", o, o.getClass());
    }

    /**
     * static 字段可以使用空对象
     */
    @Test
    public void test03() throws Exception {
        Field field = clazz.getField("password");
        Object o = field.get(null);
        log.info("filed password = {}, {}", o, o.getClass());
    }
}
