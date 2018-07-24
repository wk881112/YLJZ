package com.neo.java.io.bio;

import java.io.*;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试BIO - data IO / Object IO
 *
 * 基本数据类型的IO： boolean char byte short int long float double string
 * Object IO： 也是data io的实现
 * DataInputStream
 * DataOutputStream
 *
 * https://docs.oracle.com/javase/8/docs/technotes/guides/io/index.html
 * https://docs.oracle.com/javase/tutorial/essential/io/index.html
 */
@Slf4j
public class Test_03 {

    static final String   dataFile = "invoicedata";

    static final double[] prices   = { 19.99, 9.99, 15.99, 3.99, 4.99 };
    static final int[]    units    = { 12, 8, 13, 29, 50 };
    static final String[] descs    = { "Java T-shirt", "Java Mug", "Duke Juggling Dolls",
                                       "Java Pin", "Java Key Chain" };

    @Test
    public void test01() throws IOException {
        try (DataOutputStream out = new DataOutputStream(
            new BufferedOutputStream(new FileOutputStream(dataFile)))) {
            for (int i = 0; i < prices.length; i++) {
                out.writeDouble(prices[i]);
                out.writeInt(units[i]);
                out.writeUTF(descs[i]);
            }
        }

    }

    @Test
    public void test02() throws IOException {

        double price;
        int unit;
        String desc;
        double total = 0.0;

        try (DataInputStream in = new DataInputStream(
            new BufferedInputStream(new FileInputStream(dataFile)))) {

            while (true) {
                price = in.readDouble();
                unit = in.readInt();
                desc = in.readUTF();
                System.out.format("You ordered %d" + " units of %s at $%.2f%n", unit, desc, price);
                total += unit * price;
            }

        } catch (EOFException e) {
            System.out.println("total = " + total);
        }

    }

}
