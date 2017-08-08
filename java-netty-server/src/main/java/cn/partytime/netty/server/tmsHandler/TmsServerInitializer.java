package cn.partytime.netty.server.tmsHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

@Component
public class TmsServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    @Qualifier("tmsServerHandler")
    private TmsServerHandler tmsServerHandler;


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("idleStateHandler", new IdleStateHandler(10, 10, 0));
        //ByteBuf delimiter = Unpooled.copiedBuffer("-aa".getBytes());
        //pipeline.addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(tmsServerHandler);
    }
}
