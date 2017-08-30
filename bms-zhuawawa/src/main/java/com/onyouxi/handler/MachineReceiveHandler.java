package com.onyouxi.handler;

import com.onyouxi.service.MachineSessionService;
import com.onyouxi.service.ZhuawawaService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jack on 15/6/26.
 */
@Component
@Slf4j
public class MachineReceiveHandler extends BaseTextWebSocketHandler {

  @Autowired
  private MachineSessionService machineSessionService;

  @Autowired
  private ZhuawawaService zhuawawaService;

  @Override
  protected void handler(WebSocketSession session, String type, JSONObject json) throws IOException {
    log.info(json.toString());
    if("end".equals(type)){
      String code = machineSessionService.returnMachineCode(session);
      Integer status = json.getInt("status");
      zhuawawaService.gameOver(code,status);
    }
  }

  @Override
  public synchronized void afterConnectionEstablished(WebSocketSession session) throws Exception {
    log.info("open {}", session);
    URI uri = session.getUri();
    String query = uri.getQuery();
    if(!StringUtils.isEmpty(query)){
       if(query.indexOf("code") != -1){
         String code = query.substring(query.indexOf("code=")+5,query.length());
         log.info("code {}",code);
         machineSessionService.addMachine(code,session);
       }else{
         session.close(CloseStatus.NOT_ACCEPTABLE.withReason("no machine code"));
       }
    }else{
      session.close(CloseStatus.NOT_ACCEPTABLE.withReason("no machine code"));
    }

  }

  @Override
  public synchronized void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    log.info("close {}", session);
    machineSessionService.removeMachine(session);
  }

  @Override
  protected void handlerBrokenPipe(WebSocketSession session) throws Exception {
    log.info("close {}", session);
    machineSessionService.removeMachine(session);
  }

}
