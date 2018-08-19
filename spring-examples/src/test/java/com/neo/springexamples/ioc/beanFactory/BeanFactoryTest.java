package com.neo.springexamples.ioc.beanFactory;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.gson.Gson;
import com.neo.springexamples.ioc.beanFactory.pojo.DemoBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanFactoryTest {

    private final Gson   gson = new Gson();

    private final String pre  = "------------------------- ";

    @Test
    public void test1() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("beanFactory.xml");
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);

        BeanDefinition demoBean = factory.getBeanDefinition("demoBean");
        Assert.assertNotNull(demoBean);

        //        无法通过alias获取beanDefinition
        //        BeanDefinition demoBean2 = factory.getBeanDefinition("demoBean2");
        //        Assert.assertNotNull(demoBean2);

        DemoBean bean = factory.getBean(DemoBean.class);

        System.out.println(gson.toJson(bean));
        Assert.assertNotNull(bean);

        String[] beanDefinitionNames = factory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
            ;

        }

    }

    /**
     * bean id,name 互为alias，查询alias时，使用一个做参数会获取到其他几个。
     * 随意使用其中一个获取bean都可以
     */
    @Test
    public void test2() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(factory);
        // DefaultResourceLoader load ClassPathContextResource in DefaultResourceLoader
        xmlBeanDefinitionReader.loadBeanDefinitions("beanFactory.xml");
        String id = "demoBean";
        String[] demoBeans = factory.getAliases(id);

        if (demoBeans.length == 0) { // 如果所有name和id一样，那么就没有alias
            log.info("{} {} has no alias", pre, id);
        }

        DemoBean bean = factory.getBean(DemoBean.class);
        for (String demoBeanAlias : demoBeans) {
            log.info("{} has alias {}", pre, demoBeanAlias);
            DemoBean bean2 = factory.getBean(demoBeanAlias, DemoBean.class);
            Assert.assertEquals(bean, bean2);
        }

    }

}
