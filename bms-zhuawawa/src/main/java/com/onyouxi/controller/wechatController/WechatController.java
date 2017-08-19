package com.onyouxi.controller.wechatController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by administrator on 2017/8/19.
 */
@Controller
@RequestMapping(value = "/wechat")
@Slf4j
public class WechatController {

    @RequestMapping(value = "/zhuawawa", method = RequestMethod.GET)
    public String zhuawawa(String code, Model model, HttpServletResponse response, HttpServletRequest request){
        return "wechat/zhuawawa";
    }

}
