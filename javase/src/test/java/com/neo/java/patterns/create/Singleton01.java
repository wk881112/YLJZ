package com.neo.java.patterns.create;

/**
 * 饿汉式
 */
public class Singleton01 {
    // 私有构造
    private Singleton01(){}

    // 饿汉式
    private static final Singleton01 singleton = new Singleton01();

    public static Singleton01 getSingleton() {
        return singleton;
    }
}
