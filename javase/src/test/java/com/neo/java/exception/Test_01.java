package com.neo.java.exception;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test_01 {

    /**
     * 方法必须加以声明，表示这个方法会抛出checked exception
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        throw new Exception("yyy");
    }

    /**
     * 方法内抛运行时异常，方法声明可以不抛异常
     */
    @Test
    public void test02() {
        throw new RuntimeException("xxxx");
    }

    @Test
    public void test03() {
        String forName = "com.neo.java.exception.Test_02";
        try {
            Class<?> aClass = Class.forName(forName);
            log.info("class is {}", aClass.getName());
        } catch (ClassNotFoundException e) {
            log.info("class not found , name is {}", forName, e);
        }
    }

}