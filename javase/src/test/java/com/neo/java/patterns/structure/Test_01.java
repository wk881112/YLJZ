package com.neo.java.patterns.structure;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试模板
 */
@Slf4j
public class Test_01 {

    @Test
    public void test01() {
        Component component = new ConcreteComponent();
        ConcreteDecorator concreteDecorator = new ConcreteDecorator(component);

        ConcreteDecorator concreteDecorator1 = new ConcreteDecorator(concreteDecorator);
        ConcreteDecorator concreteDecorator2 = new ConcreteDecorator(concreteDecorator1);


        concreteDecorator.hello();
        concreteDecorator1.hello();

    }

}
