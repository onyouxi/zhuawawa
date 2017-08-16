package com.onyouxi.config;

import com.onyouxi.handler.MachineReceiveHandler;
import com.onyouxi.handler.UserH5ReceiveHandler;
import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * Created by jack on 15/5/12.
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  @Autowired
  private UserH5ReceiveHandler userH5ReceiveHandler;

  @Autowired
  private MachineReceiveHandler machineReceiveHandler;

  @Bean
  public WebSocketServerFactory webSocketServerFactory() {
    WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
//    policy.setInputBufferSize(8192);
//    policy.setIdleTimeout(60 * 1000);
    return new WebSocketServerFactory(policy);
  }

  @Bean
  public DefaultHandshakeHandler handshakeHandler() {
    return new DefaultHandshakeHandler(new JettyRequestUpgradeStrategy(webSocketServerFactory()));
  }

  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(machineReceiveHandler, "/ws/machine").addHandler(userH5ReceiveHandler,"/ws/user")
        .setAllowedOrigins("*");
  }

}
