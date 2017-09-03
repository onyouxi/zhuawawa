package com.onyouxi.service;

import com.onyouxi.model.dbModel.WechatUserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

/**
 * Created by liuwei on 16/7/11.
 */
@Service
@Slf4j
public class UserSessionService {

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private WechatUserPlayService wechatUserPlayService;

    @Autowired
    private MachineService machineService;

    private Map<String,WebSocketSession> userSessionMap = Collections.synchronizedMap(new HashMap<>());


    public boolean sendMsg(WebSocketSession session, TextMessage textMessage) {
        if (session != null && session.isOpen()) {
            try {
                synchronized (session) {
                    session.sendMessage(textMessage);
                }
                //log.info("{} sendMsg:{}", session, textMessage.getPayload());
                return true;
            } catch (IOException e) {
                log.error("ws sendMsg error", e);
            }
        }
        return false;
    }

    public void broadcast(TextMessage textMessage){
        if( null == userSessionMap || userSessionMap.size() == 0){
            return;
        }
        for(String key : userSessionMap.keySet()){
            sendMsg(userSessionMap.get(key),textMessage);
        }
    }

    public void removeUser(WebSocketSession session){
        userSessionMap.remove(session);
    }

    public void addUser(String openId,WebSocketSession session){
        userSessionMap.put(openId,session);
    }

    public String returnOpenId(WebSocketSession session){
        Set<String> keySet = userSessionMap.keySet();
        for(String key : keySet){
            WebSocketSession wss = userSessionMap.get(key);
            if(wss.equals(session)){
                return key;
            }
        }
        return null;
    }

    public void updateStatus(WebSocketSession session){
        String openId = this.returnOpenId(session);
        if(!StringUtils.isEmpty(openId)){
            WechatUserModel wechatUser = wechatUserService.findByOpenId(openId);
        }
    }
}
