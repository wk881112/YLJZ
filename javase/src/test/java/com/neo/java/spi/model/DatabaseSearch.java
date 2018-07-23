package com.neo.java.spi.model;

import java.util.List;

public class DatabaseSearch implements Search {
    @Override
    public List<String> searchDoc(String keyword) {
        System.out.println("数据库搜索 " + keyword);
        return null;
    }
}
