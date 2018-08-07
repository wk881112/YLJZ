package com.neo.java.patterns.create;

/**
 * 懒汉式
 */
public class Singleton02 {
    // 私有构造
    private Singleton02(){}

    // 懒汉式
    private volatile static Singleton02 singleton;

    public static Singleton02 getSingleton() {
        if(singleton==null) {
            synchronized (Singleton02.class) {
                if(singleton == null) {
                    singleton = new Singleton02();
                }
            }
        }
        return singleton;
    }
}
