package com.neo.java.io.nio.file;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * File IO NIO.2特性
 *
 * The java.nio.file package and its related package, java.nio.file.attribute,
 * provide comprehensive support for file I/O and for accessing the default file system.
 * Though the API has many classes, you need to focus on only a few entry points.
 * You will see that this API is very intuitive and easy to use.
 *
 * https://docs.oracle.com/javase/tutorial/essential/io/fileio.html
 *
 *
 * 本节讨论 Path
 * 文件系统：目录+文件,目录又可以包含子目录和文件。
 * 分隔符delimiter：不同操作系统不同
 *
 * 绝对路径： 绝对的,唯一的,包含所有需要的定位到路径的信息
 * 相对路径： 相对的,需要另一个路径才能确定其路径
 * 符号连接：指向目标文件的引用,对符号连接的操作会定位到对应的目标文件进行操作
 */
@Slf4j
public class Test_01 {

    String       directory = "C:\\Users\\WANGKUN";
    String       fileName  = "status.txt";

    private Path path;

    @Before
    public void bef() {
        path = Paths.get(directory, fileName);
    }

    @After
    public void aft() {
    }

    /**
     * create path in bef
     */
    @Test
    public void test01() {
        System.out.format("toString: %s%n", path.toString());
        System.out.format("getFileName: %s%n", path.getFileName());
        System.out.format("getName(0): %s%n", path.getName(0));
        System.out.format("getNameCount: %d%n", path.getNameCount());
        System.out.format("subpath(0,2): %s%n", path.subpath(0, 2));
        System.out.format("getParent: %s%n", path.getParent());
        System.out.format("getRoot: %s%n", path.getRoot());

        System.out.format("%s%n", path.toUri()); // 可以在浏览器打开，或者文件浏览器打开
    }

    /**
     * join path
     */
    @Test
    public void test02() {
        Path resolve = path.resolve(fileName);
        System.out.format("%s%n", resolve);

        // 当resolve使用绝对路径时。则返回绝对路径的path
        resolve = Paths.get("hhh").resolve(directory);
        System.out.format("%s%n", resolve);
    }

    /**
     * Files Files类是另一主要入口点java.nio.file包。该类提供了一组丰富的静态方法，用于读取，写入和操作文件和目录
     */
    @Test
    public void test03() throws IOException {
        Path target = Paths.get(directory, "status-copy.txt");
        Path move = Files.copy(path, target, REPLACE_EXISTING, ATOMIC_MOVE);

        System.out.println(move.toString());
    }

}
