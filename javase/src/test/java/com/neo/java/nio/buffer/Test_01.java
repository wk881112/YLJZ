package com.neo.java.nio.buffer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Properties;

/**
 * Buffer 是用于基本数据类型的容器（不包括boolean类型）
 *
 * 是线性有序序列: 容量、限制、位置
 *
 * capacity: 缓冲区容量不允许更改，不能为负
 * limit: 限制是第一个不能读取或写入的元素的索引，不能为负，不能大于容量。 实际上是标定写入的容量的
 * position: 位置是下一个要读取或写入的元素的索引，不能为负，不能大于容量
 *
 */
@Slf4j
public class Test_01 {

    private Buffer buffer;
    String fileName = "status.txt";

    /**
     * Path doubts
     */
    @Test
    public void testFile() {

        try {
            File directory = new File("");

            // path is what the params is
            // absolute path is a kind path from the root
            log.info(directory.getPath());
            log.info(directory.getAbsolutePath());

            log.info("---------------------------------");


            URL resource = this.getClass().getClassLoader().getResource(fileName);
            directory = new File(resource.getPath());

            log.info(directory.getPath());
            log.info(directory.getAbsolutePath());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * ByteBuffer
     */
    @Test
    public void testByteBuffer() throws Exception {

    }

}
