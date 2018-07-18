package com.neo.java.reflect;

import com.google.gson.Gson;
import com.neo.java.reflect.model.*;
import org.junit.After;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * javaBean
 *
 * http://www.cnblogs.com/pony1223/p/7450837.html
 */
@Slf4j
public class Test_05 {

    UserInfo userInfo = new UserInfo();

    @Test
    public void test01() throws Exception {

        try {
            BeanInfoUtil.getProperty(userInfo, "userName");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void test02() throws Exception {

        BeanInfoUtil.setPropertyByIntrospector(userInfo, "userName");


    }


    @After
    public void afterTest() {
        log.info("{}", userInfo);
    }

}
