package com.neo.java.io.bio;

import java.io.*;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试BIO - 缓冲流
 *
 * 由于基本IO每次操作都是直接依赖底层系统。每次连接到硬盘及其他操作非常耗时。因此引入缓冲区buffer，减少硬盘IO操作
 *
 * 一共有4中缓冲流用来包装基本IO流
 * BufferedInputStream/BufferedOutputStream
 * BufferedReader/BufferedWriter
 *
 * 什么是flushing buffered streams
 * 想在某个零界点写出缓存，而不是等待buffer满了再写出。
 * 例如：当调用PrintWriter.println或format时会autoflush
 *
 *
 * https://docs.oracle.com/javase/8/docs/technotes/guides/io/index.html
 * https://docs.oracle.com/javase/tutorial/essential/io/index.html
 */
@Slf4j
public class Test_02 {

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
     *
     * 测试字节缓冲流拷贝文件，每次拷贝一行
     *
     * BufferedReader
     * BufferedWriter
     *
     *
     *
     * 测试行级字符流，拷贝效率更高；但是低于字节缓冲流，字节缓冲流没有字符转化
     *
     *
     * 测试拷贝status.txt平均用时 140ms， 远低于test_CharacterStreams
     */
    @Test
    public void test_LineOrientedCharacterStreams() throws IOException {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        String outFileName = "status-copy.txt";

        try {
            Date now1 = new Date();
            bufferedReader = new BufferedReader(new FileReader(path));
            BufferedReader bufferedReader1 = new BufferedReader(bufferedReader);

            bufferedWriter = new BufferedWriter(new FileWriter(outFileName));

            String l;

            while ((l = bufferedReader1.readLine()) != null) {
                bufferedWriter.write(l);
            }

            Date now2 = new Date();
            log.info("cost time = {} ns", now2.getTime() - now1.getTime());

        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

}
