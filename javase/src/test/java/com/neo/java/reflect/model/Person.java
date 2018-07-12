package com.neo.java.reflect.model;

import java.io.InputStream;
import java.util.List;

public class Person {
    public String name = "Jacky";

    /******************** 构造函数 ************************/
    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, int age) {
        System.out.println("name:" + name + ",age:" + age);
    }

    private Person(List list) {
        System.out.println("list");
    }

    /******************** 一般方法 ************************/
    public String sayHello() {
        System.out.println("sayHello() 方法执行了，无参");
        return "hello";
    }

    public void sayHello(String name) {
        System.out.println("sayHello() 方法执行了");
        System.out.println("name = " + name);
    }

    public void sayHello(String name, int age) {
        System.out.println("name = " + name + ",age = " + age);
    }

    private void sayHello(List list) {
        System.out.println("List");
    }

    public static void sayHello(Person person) {
        System.out.println(person.name);
    }
}
