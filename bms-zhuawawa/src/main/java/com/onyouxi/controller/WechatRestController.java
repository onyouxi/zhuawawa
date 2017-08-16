package com.onyouxi.controller;

import com.onyouxi.utils.WechatSignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by administrator on 2017/8/16.
 */
@RestController
@RequestMapping(value = "/wechat")
@Slf4j
public class WechatRestController {

    /**
     * 微信公众平台验证用
     *
     * @param id
     */
    @RequestMapping(value = "/valid", method = RequestMethod.GET)
    public String valid(Long id, HttpServletRequest request, HttpServletResponse response) {
        //WXPlatformDto wxPlatformDto =wxPlatformService.getByPK(id);
        log.info("valid get message-----GET");

        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            token = "asdf49vmsk3fd39fgvm4xejk";
        }

        if( StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce)
                || StringUtils.isEmpty(echostr)){
            return "is null";
        }

        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (WechatSignUtil.checkSignature(signature, timestamp, nonce, token)) {
            return echostr;
        }else{
            return "error";
        }

    }
}
