package com.neo.java.patterns.structure;

public abstract class Decorator implements Component{

    protected Component decorated;

    @Override
    public void hello() {
        decorated.hello();
    }

    protected Decorator(Component component) {
        this.decorated = component;
    }

}
