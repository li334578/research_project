package com.company.sharding_sphere.other;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-05-17 21:25:01
 */
public class NettyServer {
    private static final int port = 10233;
    private static EventLoopGroup group = new NioEventLoopGroup(); // 通过nio的方式来接收连接和处理连接
    private static ServerBootstrap bootstrap = new ServerBootstrap();

    public static void main(String[] args) throws InterruptedException {
        try {
            bootstrap.group(group);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new NettyServerFilter());
            // 服务端设置绑定端口
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("服务端启动成功");
            // 监听服务器关闭监听
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}
