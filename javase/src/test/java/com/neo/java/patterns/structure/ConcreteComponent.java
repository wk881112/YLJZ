package com.neo.java.patterns.structure;

public class ConcreteComponent implements Component{
    @Override
    public void hello() {
        System.out.println("say hello");
    }
}
