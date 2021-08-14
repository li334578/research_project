package com.company.sharding_sphere.other;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-05-17 21:37:29
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    List<byte[]> datas = new ArrayList<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        ByteBuf heapBuffer = null;
        try {
            System.out.println("收到的消息" + msg);
            if ("quit".equals(msg)) {
                channelHandlerContext.close();
            }
            byte[] data = new byte[1024 * 1024];

            heapBuffer = channelHandlerContext.alloc().heapBuffer();
            heapBuffer.writeBytes(data);
            channelHandlerContext.channel().writeAndFlush(heapBuffer + "\n");
        } finally {

        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
        ctx.writeAndFlush("客户端:" + InetAddress.getLocalHost().getHostName());
        super.channelActive(ctx);
    }
}
