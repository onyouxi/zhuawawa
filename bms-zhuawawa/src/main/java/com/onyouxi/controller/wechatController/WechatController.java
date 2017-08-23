package com.onyouxi.controller.wechatController;

import com.onyouxi.model.dbModel.MachineModel;
import com.onyouxi.model.dbModel.WechatMachineModel;
import com.onyouxi.model.dbModel.WechatUserModel;
import com.onyouxi.model.dbModel.WechatUserPlayModel;
import com.onyouxi.model.pageModel.MachineResult;
import com.onyouxi.model.pageModel.WechatMachineResult;
import com.onyouxi.service.MachineService;
import com.onyouxi.service.WechatMachineService;
import com.onyouxi.service.WechatUserPlayService;
import com.onyouxi.service.WechatUserService;
import com.onyouxi.utils.WeixinUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private WechatUserPlayService wechatUserPlayService;

    @RequestMapping(value = "/zhuawawa", method = RequestMethod.GET)
    public String zhuawawa(String code, String machineId, Model model, HttpServletResponse response, HttpServletRequest request){
        String openId = WeixinUtil.getUserOpenId(code);
        WechatUserModel wechatUser = wechatUserService.findByOpenId(openId);
        List<WechatUserPlayModel> wechatUserPlayModelList = wechatUserPlayService.findByWechatUserIdAndMachineIdAndStatus(wechatUser.getId(),machineId,0);
        if( null != wechatUserPlayModelList && wechatUserPlayModelList.size() > 0){
            WechatUserPlayModel wechatUserPlayModel = wechatUserPlayModelList.get(0);
            long gameTime = new Date().getTime() - wechatUserPlayModel.getStartTime().getTime();
            model.addAttribute("gameTime",gameTime);
            model.addAttribute("gameStatus",0);
        }else{
            MachineResult machineResult = machineService.findByMachineId(machineId);
            List<WechatMachineResult> wechatMachineResultList = wechatMachineService.findResultByMachineId(machineId);
            model.addAttribute("websocketUrl",websocketUrl);
            model.addAttribute("machine",machineResult);
            model.addAttribute("user",wechatUser);
            model.addAttribute("wechatMachineList",wechatMachineResultList);
            model.addAttribute("gameStatus",1);
        }

        return "wechat/index";
    }

}
