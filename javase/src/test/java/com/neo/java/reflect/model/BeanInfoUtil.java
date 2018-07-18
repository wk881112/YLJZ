package com.neo.java.reflect.model;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanInfoUtil {

    // 设置bean的某个属性值
    public static void setProperty(UserInfo userInfo, String userName) throws Exception {
        // 获取bean的某个属性的描述符
        PropertyDescriptor propDesc = new PropertyDescriptor(userName, UserInfo.class);
        // 获得用于写入属性值的方法
        Method methodSetUserName = propDesc.getWriteMethod();
        // 写入属性值
        methodSetUserName.invoke(userInfo, "wong");
    }

    // 获取bean的某个属性值
    public static void getProperty(UserInfo userInfo, String userName) throws Exception {
        // 获取Bean的某个属性的描述符
        PropertyDescriptor proDescriptor = new PropertyDescriptor(userName, UserInfo.class);
        // 获得用于读取属性值的方法
        Method methodGetUserName = proDescriptor.getReadMethod();
        // 读取属性值
        Object invoke = methodGetUserName.invoke(userInfo);
        System.out.println(invoke);
    }

    // 通过内省设置bean的某个属性值
    public static void setPropertyByIntrospector(UserInfo userInfo,
                                                 String userName) throws Exception {
        // 获取bean信息
        BeanInfo beanInfo = Introspector.getBeanInfo(UserInfo.class);
        // 获取bean的所有属性列表
        PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
        int count = 0;
        // 遍历属性列表，查找指定的属性
        if (proDescrtptors != null && proDescrtptors.length > 0) {
            for (PropertyDescriptor propDesc : proDescrtptors) {
                count++;
                log.info("UserInfo properties no.{} is \"{}\"", count, propDesc);

                // 找到则写入属性值
                if (propDesc.getName().equals(userName)) {
                    Method methodSetUserName = propDesc.getWriteMethod();
                    methodSetUserName.invoke(userInfo, "alan"); // 写入属性值
                    break;
                }
            }
        }
    }

    // 通过内省获取bean的某个属性值
    public static void getPropertyByIntrospector(UserInfo userInfo,
                                                 String userName) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(UserInfo.class);
        PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
        if (proDescrtptors != null && proDescrtptors.length > 0) {
            for (PropertyDescriptor propDesc : proDescrtptors) {
                if (propDesc.getName().equals(userName)) {
                    Method methodGetUserName = propDesc.getReadMethod();
                    Object objUserName = methodGetUserName.invoke(userInfo);
                    System.out.println("get userName:" + objUserName.toString());
                    break;
                }
            }
        }
    }
}
