package com.onyouxi.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by administrator on 2017/8/8.
 */
@Service
public class ConfigUtil {

    @Value("${ip}")
    private String ip;

    @Value("${port}")
    private Integer port;

    @Value("${code}")
    private String code;

    public String getWebSocketUrl(){
        return "http://"+ip+":"+port+"/ws/machine?code="+this.getCode();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
