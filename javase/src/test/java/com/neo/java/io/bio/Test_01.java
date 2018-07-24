package com.neo.java.io.bio;

import java.io.*;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试BIO - 非缓冲流
 * https://docs.oracle.com/javase/8/docs/technotes/guides/io/index.html
 * https://docs.oracle.com/javase/tutorial/essential/io/index.html
 *
 * I/O Streams - 简化IO操作的模型
 * File I/O    - 文件操作
 */
@Slf4j
public class Test_01 {

    String fileName    = "status.txt";
    String path;

    @Before
    public void bef() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        path = classLoader.getResource(fileName).getPath();
    }

    @After
    public void aft() {

    }

    /**
     * Path doubts
     * 测试项目路径问题
     */
    @Test
    public void testProjectPath() {

        try {
            File directory = new File("");

            // path is what the params is
            // absolute path is a kind path from the root
            log.info(directory.getPath());
            log.info(directory.getAbsolutePath());

            log.info("---------------------------------");

            directory = new File(fileName);

            // path is what the params is
            // absolute path is a kind path from the root
            log.info(directory.getPath());
            log.info(directory.getAbsolutePath());

            log.info("---------------------------------");
            directory = new File(this.path);

            log.info(directory.getPath());
            log.info(directory.getAbsolutePath());

        } catch (Exception e) {
            log.error("", e);
        }

    }

    /**
     * 测试字节流
     *
     * FileInputStream
     * FileOutputStream
     *
     * 测试字节流拷贝文件，每次拷贝一个字节
     *
     * 什么时候不使用字节流：一下代码是一种比较低级别的IO操作，应该避免使用。由于源文件是文本文件，最好的方式是使用字符流。
     * 同样也存在其他应对复杂数据类型的流。字节流应该仅仅用在最原始IO
     *
     * 测试拷贝status.txt平均用时 22566ms
     */
    @Test
    public void test_ByteStreams() throws IOException {

        FileInputStream in = null;
        FileOutputStream out = null;
        String outFileName = "status-copy.txt";

        try {
            Date now1 = new Date();
            in = new FileInputStream(path);
            out = new FileOutputStream(outFileName);

            int c;

            while ((c = in.read()) != -1) {
                out.write(c);
            }

            Date now2 = new Date();
            log.info("cost time = {} ns", now2.getTime() - now1.getTime());
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     *测试字符流
     *
     * 测试字节流拷贝文件，每次拷贝一个字符
     *
     * The character stream uses the byte stream to perform the physical I/O
     * while the character stream handles translation between characters and bytes
     *
     * FileReader
     * FileWriter
     *
     * 测试拷贝status.txt平均用时 760ms， 远远低于test_ByteStreams
     */
    @Test
    public void test_CharacterStreams() throws IOException {
        FileReader reader = null;
        FileWriter writer = null;
        String outFileName = "status-copy.txt";

        try {
            Date now1 = new Date();
            reader = new FileReader(path);
            writer = new FileWriter(outFileName);

            int c;

            while ((c = reader.read()) != -1) {
                writer.write(c);
            }

            Date now2 = new Date();
            log.info("cost time = {} ns", now2.getTime() - now1.getTime());

        } finally {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

}
