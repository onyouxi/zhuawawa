package com.onyouxi.handler;

import com.onyouxi.service.MachineSessionService;
import com.onyouxi.service.UserSessionService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.URI;

/**
 * Created by liuwei on 16/7/11.
 */
@Component
@Slf4j
public class UserH5ReceiveHandler extends BaseTextWebSocketHandler {

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private MachineSessionService machineSessionService;

    @Override
    protected void handler(WebSocketSession session, String type, JSONObject json) throws IOException {
        log.info(json.toString());
        if("action".equals(type)){
            String name = json.getString("name");
            String code = json.getString("code");
            machineSessionService.action(code,name);
        }
    }

    @Override
    public synchronized void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("open user{}", session);
        URI uri = session.getUri();
        String query = uri.getQuery();
        if(!StringUtils.isEmpty(query)){
            if(query.indexOf("openId") != -1){
                String openId = query.substring(query.indexOf("openId=")+7,query.length()-1);
                log.info("openId {}",openId);
                userSessionService.addUser(openId,session);
            }else{
                session.close(CloseStatus.NOT_ACCEPTABLE.withReason("no openId"));
            }
        }else{
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("no openId"));
        }

    }

    @Override
    public synchronized void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("close user {}", session);
        userSessionService.removeUser(session);
    }

    @Override
    protected void handlerBrokenPipe(WebSocketSession session) throws Exception {
        log.info("close user {}", session);
        userSessionService.removeUser(session);
    }
}
