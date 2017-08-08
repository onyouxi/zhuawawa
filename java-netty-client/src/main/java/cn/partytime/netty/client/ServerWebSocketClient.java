/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package cn.partytime.netty.client;

import cn.partytime.netty.client.handler.ServerWebSocketClientHandler;
import cn.partytime.util.ConfigUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URI;

@Slf4j
@Component
public final class ServerWebSocketClient {

    @Autowired
    @Qualifier("serverWebSocketClientHandler")
    private ServerWebSocketClientHandler serverWebSocketClientHandler;
    @Autowired
    private ConfigUtil configUtil;

    public  void init() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = null;
        try {
            URI uri = new URI("http://"+configUtil.getIp()+":"+configUtil.getPort());
            final int port = uri.getPort();
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) {
                     ChannelPipeline p = ch.pipeline();
                     p.addLast(
                             new HttpClientCodec(),
                             new HttpObjectAggregator(8192),
                             new IdleStateHandler(10, 10, 0),
                             WebSocketClientCompressionHandler.INSTANCE,
                             serverWebSocketClientHandler);
                 }
             });
           channelFuture = b.connect(uri.getHost(), port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(channelFuture!=null){
                if(channelFuture.channel()!=null && channelFuture.channel().isOpen()){
                    channelFuture.channel().close();
                }
            }
            group.shutdownGracefully();
            log.info("远程服务器连接不上，重新接连");
            init();
        }
    }
}
