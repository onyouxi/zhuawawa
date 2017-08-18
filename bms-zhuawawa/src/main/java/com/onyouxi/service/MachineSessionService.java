package com.onyouxi.service;


import com.onyouxi.constant.Const;
import com.onyouxi.model.MachineSokcetModel;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jack on 15/6/26.
 */
@Service
@Slf4j
public class MachineSessionService {

  private Map<String,WebSocketSession> machineSessionMap = Collections.synchronizedMap(new HashMap<>());


  public void addMachine(String machineCode, WebSocketSession session) {
      machineSessionMap.put(machineCode,session);
  }

  public void removeMachine(WebSocketSession session) {
    machineSessionMap.remove(session);
  }

  public void action(String code,String name){
    WebSocketSession session = machineSessionMap.get(code);
    MachineSokcetModel machineSokcetModel = new MachineSokcetModel();
    if(Const.LEFT.equals(name)){
      machineSokcetModel.setCmd(Const.LEFT);
    }else if(Const.RIGHT.equals(name)){
      machineSokcetModel.setCmd(Const.RIGHT);
    }else if(Const.UP.equals(name)){
      machineSokcetModel.setCmd(Const.UP);
    }else if(Const.DOWN.equals(name)){
      machineSokcetModel.setCmd(Const.DOWN);
    }else if(Const.END.equals(name)){
      machineSokcetModel.setCmd(Const.END);
    }else if(Const.ZHUA.equals(name)){
      machineSokcetModel.setCmd(Const.ZHUA);
    }
    TextMessage textMessage = new TextMessage(JSONObject.fromObject(machineSokcetModel).toString());
    sendMsg(session,textMessage);

  }

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


}
