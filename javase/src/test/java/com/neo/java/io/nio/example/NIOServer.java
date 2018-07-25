package com.neo.java.io.nio.example;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 *这个测试有问题
 *
 使用8个线程，每个任务处理时间为 500ms。

 50个连接，线程池模式耗时 2995，NIO模式耗时 2996
 200个连接，线程池模式耗时 11954，NIO模式耗时 11946
 2000个连接，线程池模式耗时 176274，NIO模式耗时 123692
 */
@Slf4j
public class NIOServer extends Thread {

    private ExecutorService executor;

    public static void main(String[] args) throws InterruptedException {
        NIOServer demoServer = new NIOServer();
        demoServer.start();

        Thread.sleep(1000L); // 等待服务端准备好
        for (int i = 0; i < 20000; i++) {
            try (SocketChannel socket = SocketChannel.open()) {
                socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 8890));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {

        try (Selector selector = Selector.open();
                ServerSocketChannel serverSocket = ServerSocketChannel.open()) {

            serverSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), 8890));
            serverSocket.configureBlocking(false);

            // 注册到 Selector 并说明关注点
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            executor = Executors.newFixedThreadPool(8);

            while (true) {
                selector.select(); // 阻塞等待就绪的channel

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                if (selectionKeys.size() <= 0) {
                    log.warn("no keys");
                    continue;
                }

                selectionKeys.forEach(s -> {
                    ServerSocketChannel channel = (ServerSocketChannel) s.channel();
                    try {
                        SocketChannel socket = channel.accept();
                        RequestHandler requestHandler = new RequestHandler(socket);
                        executor.execute(requestHandler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sayHelloWorld(SocketChannel client) throws IOException, InterruptedException {
//        Thread.sleep(496L); // 模拟处理过程耗时
        if (client == null) {
            log.warn("client is null");
            return;
        }
        log.info("handling...");
        client.write(Charset.defaultCharset().encode("Hello World!"));
    }

    class RequestHandler extends Thread {

        SocketChannel socket;

        RequestHandler(SocketChannel socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                sayHelloWorld(socket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
