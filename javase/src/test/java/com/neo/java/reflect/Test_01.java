package com.neo.java.reflect;

import com.neo.java.reflect.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class Test_01 {

    /**
     * 反射,加载类,获取类的字节码
     * @throws Exception
     */
    @Test
    public void test() throws Exception

    {

        //1.

        Class<?> clazz = Class.forName("com.neo.java.reflect.model.Person");

        //2.

        Class<?> clazz1 = new Person().getClass();

        //3.

        Class<?> clazz2 = Person.class;

        log.info("class 1: {}", clazz);
        log.info("class 2: {}", clazz1);
        log.info("class 3: {}", clazz2);

        /**
        
         * 通过上面的三种方式,加载类已经完成，获取到了类的字节码，那么Class 提供了那些方法呢？
        
         * public Constructor<T> getConstructor(Class<?>... parameterTypes)
        
         public Method getMethod(String name,Class<?>... parameterTypes)
        
         public Field getField(String name)
        
         *
        
         * 也就说，和定义里面说的一样，我们可以拿到类的属性，方法 ，构造函数等相关的东西
        
         */

    }
}
