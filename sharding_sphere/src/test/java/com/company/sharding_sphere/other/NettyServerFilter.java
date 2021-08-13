package com.company.sharding_sphere.other;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-05-17 21:32:58
 */
public class NettyServerFilter extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 以("\n")为结尾分割的 解码器
        pipeline.addLast("framer",new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        // 解码和编码 应和客户端一致
        pipeline.addLast("decoder",new StringDecoder());
        pipeline.addLast("encoder",new StringDecoder());
        pipeline.addLast("handler",new NettyServerHandler());
    }
}
