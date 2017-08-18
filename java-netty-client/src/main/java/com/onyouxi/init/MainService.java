package com.onyouxi.init;

import com.onyouxi.netty.client.ServerWebSocketClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/23 0023.
 */
@Slf4j
@Service
public class MainService {

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private ServerWebSocketClient serverWebSocketClient;

    /**
     * 启动系统加载项目
     */
    @PostConstruct
    public void init() {
        /**
         * 启动第一个客户端连接远程server
         */
        log.info("启动连接远程服务器的client");
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    serverWebSocketClient.init();
                } catch (Exception e) {
                    //e.printStackTrace();
                    log.info("启动连接远程服务器的client异常:"+e.getMessage());
                }
            }
        });

    }

}
