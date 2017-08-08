package cn.partytime.netty.server.clienthandler;

import cn.partytime.config.ClientCache;
import cn.partytime.model.RestResultModel;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by lENOVO on 2016/12/6.
 */

@Component
@Qualifier("webSocketServerHandler")
@ChannelHandler.Sharable
public class HttpHandler extends SimpleChannelInboundHandler<Object> {

    @Autowired
    private ClientCache clientCache;

    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object object) throws Exception {
        if (object instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) object);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        String url = req.uri();
        logger.info("建立连接url:{}",url);
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(url);
        Map<String, List<String>> parameters = queryStringDecoder.parameters();
        RestResultModel restResultModel= new RestResultModel();

        if(url.startsWith("/door/pickUpPhone")){
            restResultModel.setResult(200);
            restResultModel.setData("success");
            Channel channel = clientCache.getChannel("test");
            if( null != channel){
                for(int i=0;i<3;i++){
                    ByteBuf resp = Unpooled.copiedBuffer("a".getBytes());
                    channel.writeAndFlush(resp);
                }
            }
            httpHandler(url,ctx,req, JSON.toJSONString(restResultModel));
            return;
        }

        if(url.startsWith("/door/hangUpPhone")){
            restResultModel.setResult(200);
            restResultModel.setData("success");
            Channel channel = clientCache.getChannel("test");
            if( null != channel){
                for(int i=0;i<3;i++) {
                    ByteBuf resp = Unpooled.copiedBuffer("b".getBytes());
                    channel.writeAndFlush(resp);
                }
            }
            httpHandler(url,ctx,req, JSON.toJSONString(restResultModel));
            return;
        }

        if(url.startsWith("/door/openDoor")){
            restResultModel.setResult(200);
            restResultModel.setData("success");
            Channel channel = clientCache.getChannel("test");
            if( null != channel){
                for(int i=0;i<3;i++) {
                    ByteBuf resp = Unpooled.copiedBuffer("c".getBytes());
                    channel.writeAndFlush(resp);
                }
            }
            httpHandler(url,ctx,req, JSON.toJSONString(restResultModel));
            return;
        }

        if(url.startsWith("/door/send")){
            restResultModel.setResult(200);
            restResultModel.setData("success");
            httpHandler(url,ctx,req, JSON.toJSONString(restResultModel));
            return;
        }
    }

    private void httpHandler(String url,ChannelHandlerContext ctx,FullHttpRequest req,String message){
        FullHttpResponse response = findFullHttpResponse(req,message);
        ctx.write(response);
        ctx.flush();
        return;
    }


    private FullHttpResponse findFullHttpResponse(FullHttpRequest req,String message){
        FullHttpResponse response = null;
        try {
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(message.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.headers().set(CONTENT_TYPE, "text/plain");
        response.headers().set(CONTENT_LENGTH,
                response.content().readableBytes());
        if (HttpHeaders.isKeepAlive(req)) {
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        return response;
    }


}
