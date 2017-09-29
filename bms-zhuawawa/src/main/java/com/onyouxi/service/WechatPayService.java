package com.onyouxi.service;

import com.onyouxi.utils.ConfigUtil;
import com.onyouxi.utils.WechatSignUtil;
import com.onyouxi.utils.WeixinUtil;
import com.onyouxi.wechat.entity.ReceiveUnifiedOrderXmlEntity;
import com.onyouxi.wechat.pojo.AccessToken;
import com.onyouxi.wechat.pojo.WxJsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by administrator on 2017/9/28.
 */
@Service
@Slf4j
public class WechatPayService {

    @Autowired
    private ConfigUtil configUtil;



    public WxJsConfig createWxjsConfig(String url) {
        AccessToken accessToken = new WeixinUtil().getAccessToken();
        if( null != accessToken){
            String jsTicket = WeixinUtil.getJsTicket(accessToken.getToken());
            WxJsConfig wxJsConfig = WechatSignUtil.jsSignature(url, jsTicket);
            return wxJsConfig;
        }else{
            return null;
        }

    }

    public Map<String, String> createUnifiedorder(String nonceStr, String timestamp, String openId, String body, String detail, String attach, Integer total_fee, String spbillCreateIp) {
        ReceiveUnifiedOrderXmlEntity receiveUnifiedOrderXmlEntity = WeixinUtil.unifiedorder(nonceStr,body, detail, attach, total_fee, spbillCreateIp, openId);
        Map<String, String> map = new HashMap<>();
        String packageStr = "prepay_id=" + receiveUnifiedOrderXmlEntity.getPrepay_id();
        String signType = "MD5";

        String jsPayStr = "appId=" + configUtil.getAppId() + "&nonceStr=" + nonceStr +
                "&package=" + packageStr +
                "&signType=" + signType +
                "&timeStamp=" + timestamp;

        map.put("paySign", WechatSignUtil.createPaySign(jsPayStr));
        map.put("packageStr", packageStr);
        map.put("signType", signType);
        map.put("timeStamp",timestamp);
        map.put("nonceStr",nonceStr);
        return map;
    }
}
