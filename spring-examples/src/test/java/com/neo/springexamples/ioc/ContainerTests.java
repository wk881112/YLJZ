package com.neo.springexamples.ioc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.neo.springexamples.ioc.pojo.JpaAccountDao;
import com.neo.springexamples.ioc.pojo.PetStoreServiceImpl;
import com.neo.springexamples.ioc.pojo.UserService;

public class ContainerTests {

    /**
     * only基于spring-beans命名空间 XML形式的 beanDefinition
     */
    @Test
    public void test01() {
        ApplicationContext context = new ClassPathXmlApplicationContext("services.xml");
        UserService userService = context.getBean("userService", UserService.class);
        Assert.assertEquals("abc", userService.getDemoStr());
    }

    /**
     * 使用多个配置文件
     * 通常，每个单独的XML配置文件都代表了体系结构中的逻辑层或模块
     * import指定的都是相对路径，开始的斜杠会被忽略，剩余的部分作为相对路径，最好不要使用斜杠，以免引起歧义
     *
     * 包括目标xml文件的顶层元素<beans><beans/> 都会被导入，只要符合schema的规范
     */
    @Test
    public void test02() {
        ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
        PetStoreServiceImpl petStore = context.getBean("petStore", PetStoreServiceImpl.class);
        JpaAccountDao accountDao = context.getBean("accountDao", JpaAccountDao.class);
        Assert.assertEquals(petStore.getAccountDao(), accountDao);
    }

    @Test
    public void test03() {
        GenericApplicationContext genericApplicationContext = new GenericApplicationContext();
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
            genericApplicationContext);
    }

}