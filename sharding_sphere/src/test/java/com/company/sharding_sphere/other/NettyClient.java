package com.company.sharding_sphere.other;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-05-17 21:43:54
 */
public class NettyClient {
    private static final String host = "127.0.0.1"; // ip地址
    private static final int port = 10233;
    private static EventLoopGroup group = new NioEventLoopGroup(); // 通过nio的方式来接收连接和处理连接
    private static Bootstrap bootstrap = new Bootstrap();
    private static Channel channel;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("客户端启动");
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
//        bootstrap.handler(new NettyServerFilter());
        // 连接服务器
        channel = bootstrap.connect(host, port).sync().channel();
        sendMsg();
    }

    public static void sendMsg() {
        for (int i = 0; i < 1000000000; i++) {
            String str = "hello Netty";
            channel.writeAndFlush(str + "\r\n");
            System.out.println("客户端发送数据" + str);
        }
    }
}
