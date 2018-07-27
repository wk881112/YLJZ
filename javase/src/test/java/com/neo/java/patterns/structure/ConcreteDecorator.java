package com.neo.java.patterns.structure;

public class ConcreteDecorator extends Decorator{

    public ConcreteDecorator(Component component) {
        super(component);
    }

    public void hello() {
        System.out.println("执行包装前置");
        super.hello();
        System.out.println("执行包装后置");
    }

}
