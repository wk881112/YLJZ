package com.neo.java.patterns.create;

/**
 * 懒汉式
 */
public class Singleton03 {
    // 私有构造
    private Singleton03(){}

    // 懒汉式 - 静态内部类
    private volatile static Singleton03 singleton;

    static class Singleton03Holder {
        private static final  Singleton03 singleton = new Singleton03();
    }

    public static Singleton03 getSingleton() {
        return Singleton03Holder.singleton;
    }
}
