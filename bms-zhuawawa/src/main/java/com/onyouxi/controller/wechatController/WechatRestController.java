package com.onyouxi.controller.wechatController;

import com.onyouxi.constant.Const;
import com.onyouxi.model.dbModel.WechatUserModel;
import com.onyouxi.service.WechatUserService;
import com.onyouxi.utils.MessageUtil;
import com.onyouxi.utils.WechatSignUtil;
import com.onyouxi.utils.WeixinUtil;
import com.onyouxi.wechat.entity.ReceiveXmlEntity;
import com.onyouxi.wechat.message.TextMessage;
import com.onyouxi.wechat.process.FormatXmlProcess;
import com.onyouxi.wechat.process.ReceiveXmlProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by administrator on 2017/8/16.
 */
@RestController
@RequestMapping(value = "/wechat")
@Slf4j
public class WechatRestController {

    @Autowired
    private WechatUserService wechatUserService;

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


    /**
     * 解析处理xml、获取智能回复结果（通过图灵机器人api接口）
     *
     * @return 最终的解析结果（xml格式数据）
     */
    @RequestMapping(value = "/valid", method = RequestMethod.POST)
    public String processWechatMag(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("valid get message-----POST");

        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        /** 解析xml数据 */
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(request);
        log.info("valid post:" + xmlEntity.toString());
        //按照touserName 得到公众帐号信息
        String wechatId = xmlEntity.getToUserName();
        String openId = xmlEntity.getFromUserName();


        String result = "";
        //消息类型为event
        if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(xmlEntity.getMsgType())) {
            log.info("event------->"+xmlEntity.getEvent());
            //当用户同意允许公众账号获取地理位置时，每次打开微信公众账号，都会收到此消息
            if (MessageUtil.REQ_MESSAGE_TYPE_LOCATION.equals(xmlEntity.getEvent())) {
                log.info("REQ_MESSAGE_TYPE_LOCATION" + xmlEntity.getContent());

                //关注微信
            } else if (MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(xmlEntity.getEvent())) {
                log.info("EVENT_TYPE_SUBSCRIBE" + xmlEntity.getContent());
                wechatUserService.save(openId);
                TextMessage text = new TextMessage();
                text.setToUserName(xmlEntity.getFromUserName());
                text.setFromUserName(xmlEntity.getToUserName());
                text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                text.setCreateTime(new Date().getTime());
                text.setFuncFlag(0);
                text.setContent(Const.WELCOME);
                result = FormatXmlProcess.textMessageToXml(text);
                //取消关注
            } else if (MessageUtil.EVENT_TYPE_UNSUBSCRIBE.equals(xmlEntity.getEvent())) {
                log.info("EVENT_TYPE_UNSUBSCRIBE" + xmlEntity.getContent());
                wechatUserService.unsubscribe(openId);
                //CLICK事件推送
            } else if (MessageUtil.EVENT_TYPE_CLICK.equals(xmlEntity.getEvent())) {

                String eventKey = xmlEntity.getEventKey();
                if (!StringUtils.isEmpty(eventKey)) {
                    if (eventKey.equals("VOICE_DAN_MU")) {


                    } else if (eventKey.equals("MONEY")) {

                    } else if (eventKey.equals("CONTECT_US")) {

                    }
                }
                log.info("EVENT_TYPE_CLICK" + eventKey);
                return result;
                //view事件推送
            } else if (MessageUtil.EVENT_TYPE_VIEW.equals(xmlEntity.getEvent())) {
                String eventKey = xmlEntity.getEventKey();
                //处理view事件推送,主要记录用户点击事件
                String url = xmlEntity.getUrl();
                log.info("EVENT_TYPE_VIEW" + eventKey);
            }
        } else if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(xmlEntity.getMsgType())) {
            String content = xmlEntity.getContent();
            if (!StringUtils.isEmpty(content)) {
                log.info("RESP_MESSAGE_TYPE_TEXT" + content);


            }

        } else if (MessageUtil.REQ_MESSAGE_TYPE_VOICE.equals(xmlEntity.getMsgType())) {
            String recognition = xmlEntity.getRecognition();

        }
        return result;
    }

}
