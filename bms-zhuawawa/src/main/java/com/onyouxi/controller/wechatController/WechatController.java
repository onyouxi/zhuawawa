package com.onyouxi.controller.wechatController;

import com.onyouxi.model.dbModel.MachineModel;
import com.onyouxi.model.dbModel.WechatUserModel;
import com.onyouxi.service.MachineService;
import com.onyouxi.service.WechatMachineService;
import com.onyouxi.service.WechatUserService;
import com.onyouxi.utils.WeixinUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${zhuawawa.websocketUrl}")
    private String websocketUrl;

    @Autowired
    private MachineService machineService;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private WechatMachineService wechatMachineService;

    @RequestMapping(value = "/zhuawawa", method = RequestMethod.GET)
    public String zhuawawa(String code, String machineId, Model model, HttpServletResponse response, HttpServletRequest request){

        String openId = WeixinUtil.getUserOpenId(code);
        WechatUserModel wechatUser = wechatUserService.findByOpenId(openId);
        MachineModel machineModel = machineService.findById(machineId);

        model.addAttribute("websocketUrl",websocketUrl);
        model.addAttribute("machine",machineModel);
        model.addAttribute("user",wechatUser);
        return "wechat/index";
    }

}
