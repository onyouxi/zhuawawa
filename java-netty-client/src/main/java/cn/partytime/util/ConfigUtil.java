package cn.partytime.util;

/**
 * Created by administrator on 2017/8/8.
 */
public class ConfigUtil {

    private String ip;

    private Integer port;

    private String code;

    public String getWebSocketUrl(){
        return "http://"+ip+":"+port;
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
