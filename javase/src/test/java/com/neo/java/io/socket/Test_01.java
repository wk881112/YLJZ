package com.neo.java.io.socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 准备知识： TCP UDP URL
 */
@Slf4j
public class Test_01 {

    String spec = "https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html";

    @Before
    public void bef() {

    }

    @After
    public void aft() {

    }

    /**
     * Reading Directly from a URL
     *
     * URLs和URLConnections为访问Internet上的资源提供了相对高级的机制
     *
     * @throws IOException
     */
    @Test
    public void test01() throws IOException {

        URL oracle = new URL(spec);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }


    /**
     * Socket通信是基于TCP协议的
     *
     * 有时，您的程序需要较低级别的网络通信，例如，当您要编写客户端 - 服务器应用程序时。
     * 例如处理数据库查询或发送当前股票价格
     *
     * To communicate over TCP, a client program and a server program establish a connection to one another.
     * Each program binds a socket to its end of the connection.
     * To communicate, the client and the server each reads from and writes to the socket bound to the connection.
     */
    @Test
    public void test02() {


    }

}
