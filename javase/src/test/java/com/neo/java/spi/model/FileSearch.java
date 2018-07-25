package com.neo.java.spi.model;

import java.util.List;

public class FileSearch implements Search {

    // 迭代器迭代时，加载类。如果不迭代，那么类不会被加载，静态代码块不会被执行
    static {
        System.out.println("invoke static");
    }

    @Override
    public List<String> searchDoc(String keyword) {
        System.out.println("文件搜索 " + keyword);
        return null;
    }
}
