package com.neo.java.io.socket;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 套接字直接协议（SDP）
 *
 * 对于高性能计算环境，需要快速有效地在网络上移动数据的能力。这种网络通常被描述为需要高吞吐量和低延迟。
 * 高吞吐量是指可以在很长一段时间内提供大量处理能力的环境。低延迟是指处理输入和提供输出之间的最小延迟
 *
 * 在这些环境中，使用套接字流的传统网络在移动数据时可能会产生瓶颈。InfiniBand（IB）于1999年由InfiniBand贸易协会推出，旨在满足高性能计算的需求。IB最重要的特性之一是远程直接内存访问（RDMA）。RDMA可以将数据直接从一台计算机的内存移动到另一台计算机，绕过两台计算机的操作系统，从而显着提高性能。
 *
 */
@Slf4j
public class Test_05 {

    @Test
    public void test01() {

    }

}
