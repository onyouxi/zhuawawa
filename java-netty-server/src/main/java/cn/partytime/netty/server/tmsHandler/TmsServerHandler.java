package cn.partytime.netty.server.tmsHandler;

import cn.partytime.config.ClientCache;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier("tmsServerHandler")
@ChannelHandler.Sharable
public class TmsServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private ClientCache clientCache;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String command = (String)msg;
        log.info(command);
        if("a1".equals(command)){
            for(int i=0;i<3;i++) {
                Thread.sleep(1000);
                ByteBuf resp = Unpooled.copiedBuffer("b".getBytes());
                ctx.channel().writeAndFlush(resp);
                log.info("send open out door!");
            }


        }else if("a2".equals(command)){

            log.info("屋门响了，快去开门");
        }else {
            try {
                JSONObject jsonObject = JSONObject.parseObject(command);
                if (null != jsonObject) {
                    String id = jsonObject.getString("id");
                    clientCache.addChannel(id, ctx.channel());
                    Thread.sleep(1000);
                    for (int i = 0; i < 3; i++) {
                        ByteBuf resp = Unpooled.copiedBuffer("lo".getBytes());
                        ctx.channel().writeAndFlush(resp);
                    }
                    log.info("login ok");
                }
            } catch (Exception e) {
                log.error("json err");
            }
        }

        /*System.out.println("The time server receive order ："+ body+" ; the counter is :"+ ++counter);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?
                new Date(System.currentTimeMillis()).toString():"BAD ORDER";
        currentTime = currentTime +System.getProperty("line.separator");
        currentTime = currentTime + "$_";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);*/


    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                String heartStr = "h";
                Map<String,Channel> map = clientCache.getChannelMap();
                for(String name : map.keySet()){
                    Channel channel = map.get(name);
                    if( null != channel){
                        for(int i=0;i<3;i++) {
                            ByteBuf resp = Unpooled.copiedBuffer(heartStr.getBytes());
                            channel.writeAndFlush(resp);
                        }
                        log.info("send heart ok");
                    }
                }
            }
        }

    }
     **/



}