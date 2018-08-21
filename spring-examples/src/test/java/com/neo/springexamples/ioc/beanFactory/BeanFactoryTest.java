package com.neo.springexamples.ioc.beanFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.gson.Gson;
import com.neo.springexamples.ioc.beanFactory.pojo.DemoBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanFactoryTest {

    private final Gson         gson = new Gson();

    private final String       pre  = "------------------------- ";

    DefaultListableBeanFactory factory;

    @Before
    public void init() {
        log.info("Invoking test before method--");
        factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("beanFactory.xml");
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);
    }

    /**
     * 测试使用beans包里的容器
     */
    @Test
    public void test1() {
        DemoBean bean = factory.getBean(DemoBean.class);
        Assert.assertNotNull(bean);
    }

    /**
     * bean id,name 互为alias，查询alias时，使用一个做参数会获取到其他几个。
     * 随意使用其中一个获取bean都可以
     */
    @Test
    public void test2() {
        DefaultListableBeanFactory localFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(localFactory);
        // DefaultResourceLoader load ClassPathContextResource in DefaultResourceLoader
        xmlBeanDefinitionReader.loadBeanDefinitions("beanFactory.xml");
        String id = "demoBean";
        String[] demoBeans = localFactory.getAliases(id);

        if (demoBeans.length == 0) { // 如果所有name和id一样，那么就没有alias
            log.info("{} {} has no alias", pre, id);
        }

        DemoBean bean = localFactory.getBean(DemoBean.class);
        for (String demoBeanAlias : demoBeans) {
            log.info("{} has alias {}", pre, demoBeanAlias);
            DemoBean bean2 = localFactory.getBean(demoBeanAlias, DemoBean.class);
            Assert.assertEquals(bean, bean2);
        }

    }

    /**
     * 测试beanDefinition
     */
    @Test
    public void test3() {

        //        无法通过alias获取beanDefinition,如果不设定bean id 则使用类权限顶名称#1这样的格式来存储
        //        BeanDefinition demoBean2 = factory.getBeanDefinition("demoBean2");
        //        Assert.assertNotNull(demoBean2);

        String[] beanDefinitionNames = factory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

        BeanDefinition demoBean = factory.getBeanDefinition("demoBean");
        Assert.assertNotNull(demoBean);
        System.out.println(demoBean);
        System.out.println(demoBean.getClass().getName());
    }

    /**
     * 测试生命周期之AbstractBeanDefinition init destroy
     *
     * DefaultListableBeanFactory 没有实现这个周期
     */
    @Test
    public void test4() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "beanFactory.xml");
        context.destroy();
    }

    /**
     * 测试相同ID的情况 beanFactory2.xml如果在 beanFactory.xml的id="demoBean"定义后导入，则该测试无法通过
     *
     * 名称相同，后面的定义会覆盖前面的定义。类似与map.put
     */
    @Test
    public void test5() {
        Object demoBean = factory.getBean("demoBean");
        Assert.assertEquals(demoBean.getClass(), DemoBean.class);
    }

}
