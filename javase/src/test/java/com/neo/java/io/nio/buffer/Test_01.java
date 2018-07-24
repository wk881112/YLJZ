package com.neo.java.io.nio.buffer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.nio.Buffer;

/**
 * Buffer 是用于基本数据类型的容器（不包括boolean类型）
 *
 * 是线性有序序列: 容量、限制、位置
 *
 * capacity: 缓冲区容量不允许更改，不能为负
 * limit: 限制是第一个不能读取或写入的元素的索引，不能为负，不能大于容量。 实际上是标定写入的容量的
 * position: 位置是下一个要读取或写入的元素的索引，不能为负，不能大于容量
 *
 * http://www.cnblogs.com/pony1223/p/8179804.html buffer channel
 *
 * http://www.cnblogs.com/pony1223/p/8138233.html  IO/NIO主要区别
 *
 *
 * https://docs.oracle.com/javase/8/docs/technotes/guides/io/index.html
 *
 */
@Slf4j
public class Test_01 {

    /**
     * ByteBuffer
     */
    @Test
    public void testByteBuffer() throws Exception {

    }

}
