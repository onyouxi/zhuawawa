package cn.partytime.service;

import cn.partytime.netty.server.ClientServer;
import cn.partytime.netty.server.TmsServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/4/14 0014.
 */

@Slf4j
@Service
public class ServerStartService {

    @Value("${netty.port:8484}")
    private int clientSeverPort;

    @Value("${netty.port:9099}")
    private int tmsServerPort;

    @Autowired
    private ClientServer clientServer;

    @Autowired
    private TmsServer tmsServer;

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    public void projectInit(){
        startClientServer();
        startNettyServer();
    }

    /**
     * 启动netty Server
     */
    private void startNettyServer(){
        try {
            log.info("arduino server start");
            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    clientServer.init(clientSeverPort);
                }
            });
        }catch (Exception e){
            log.error("",e);
        }
    }

    /**
     * 启动netty Server
     */
    private void startClientServer(){
        log.info("启动服务本地JavaClient的clientServer");
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    tmsServer.init(tmsServerPort);
                } catch (Exception e) {
                    //e.printStackTrace();
                    log.error("启动服务本地JavaClient的clientServer异常:"+e.getMessage());
                }
            }
        });
    }

}
