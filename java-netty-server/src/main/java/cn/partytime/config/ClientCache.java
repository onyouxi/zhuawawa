package cn.partytime.config;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by administrator on 2017/5/3.
 */
@Component
public class ClientCache {

    private ConcurrentHashMap<String,Channel> channelHashMap = new ConcurrentHashMap<>();

    public void addChannel(String name,Channel channel){
        channelHashMap.put(name,channel);
    }

    public Channel getChannel(String name){
        return channelHashMap.get(name);
    }

    public ConcurrentHashMap<String,Channel> getChannelMap(){
        return channelHashMap;
    }



}
