package com.neo.java.io.nio.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * NIO能解决什么问题
 *
 * 使用8个线程，每个任务处理时间约为500ms。总耗时约3秒
 */
@Slf4j
public class DemoServer extends Thread {

    private ServerSocket    serverSocket;
    private ExecutorService executor;
    private int             count;

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public static void main(String[] args) throws InterruptedException {
        DemoServer demoServer = new DemoServer();
        demoServer.start();

        Thread.sleep(1000L); // 等待服务端准备好
        for (int i = 0; i < 20000; i++) {
            try (Socket client = new Socket(InetAddress.getLocalHost(), demoServer.getPort())) {
                //                BufferedReader bufferedReader = new BufferedReader(
                //                    new InputStreamReader(client.getInputStream()));
                //
                //                bufferedReader.lines().forEach(s -> System.out.println(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            // 端口0表示自动绑定一个空闲端口
            serverSocket = new ServerSocket(0);

            executor = Executors.newFixedThreadPool(8);

            while (true) {
                // 阻塞等待客户端连接
                Socket socket = serverSocket.accept();
//                log.info("get connect! {}", ++count); // 测试阻塞

                RequestHandler requestHandler = new RequestHandler(socket);
                // version 1.0 每次接收到请求就会启动一个新线程，开销大
                // requestHandler.start();

                // version 2.0 使用8个线程的线程池处理
                executor.execute(requestHandler);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class RequestHandler extends Thread {

        private Socket socket;

        RequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
//                Thread.sleep(496L); // 模拟处理过程耗时
                log.info("handling...");
                out.println("Hello world!");
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
