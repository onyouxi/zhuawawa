package com.onyouxi.wechat.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/1/24.
 */
public class WxJsConfig implements Serializable{
    private static final long serialVersionUID = 1L;

    public String url;
    public String jsapi_ticket;
    public String nonceStr;
    public String timestamp;
    public String signature;
    private String appId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJsapi_ticket() {
        return jsapi_ticket;
    }

    public void setJsapi_ticket(String jsapi_ticket) {
        this.jsapi_ticket = jsapi_ticket;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "WxJsConfig{" +
                "url='" + url + '\'' +
                ", jsapi_ticket='" + jsapi_ticket + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
