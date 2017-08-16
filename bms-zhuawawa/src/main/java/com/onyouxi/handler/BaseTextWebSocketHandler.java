package com.onyouxi.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * Created by jack on 15/7/1.
 */
@Slf4j
public abstract class BaseTextWebSocketHandler extends TextWebSocketHandler {

  protected abstract void handler(WebSocketSession session, String type, JSONObject json) throws IOException;

  protected abstract void handlerBrokenPipe(WebSocketSession session) throws Exception;

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    if (message != null && !StringUtils.isEmpty(message)) {
      try {
        JSONObject json = JSON.parseObject(message.getPayload());
        if (json.containsKey("type")) {
          String type = json.getString("type");
          handler(session, type, json);
          return;
        }
      } catch (JSONException e) {

      } catch (Exception e) {
        log.error("", e);

      }
    }
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    String cause = exception.getCause() + "";
    if (cause.contains("java.io.IOException: Broken pipe")
        || cause.contains("java.io.IOException: 断开的管道")) {
      log.warn("{} is broken pipe!", session);
      handlerBrokenPipe(session);
    } else {
      log.error("", exception);
    }
  }

}
