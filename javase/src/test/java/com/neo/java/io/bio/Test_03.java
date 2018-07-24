package com.neo.java.io.bio;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * 测试BIO - data IO
 *
 * 基本数据类型的IO： boolean char byte short int long float double string
 *
 * DataInputStream
 * DataOutputStream
 *
 * https://docs.oracle.com/javase/8/docs/technotes/guides/io/index.html
 * https://docs.oracle.com/javase/tutorial/essential/io/index.html
 */
@Slf4j
public class Test_03 {

    String fileName    = "status.txt";
    String path;

    static final String dataFile = "invoicedata";

    static final double[] prices = { 19.99, 9.99, 15.99, 3.99, 4.99 };
    static final int[] units = { 12, 8, 13, 29, 50 };
    static final String[] descs = {
            "Java T-shirt",
            "Java Mug",
            "Duke Juggling Dolls",
            "Java Pin",
            "Java Key Chain"
    };

    @Before
    public void bef() {
    }

    @After
    public void aft() {

    }

    @Test
    public void test01() throws IOException {
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream(dataFile)));

        for (int i = 0; i < prices.length; i++) {
            out.writeDouble(prices[i]);
            out.writeInt(units[i]);
            out.writeUTF(descs[i]);
        }

    }

}
