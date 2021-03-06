package com.onyouxi.controller.wechatController;

import com.onyouxi.model.dbModel.MachineModel;
import com.onyouxi.model.dbModel.WechatMachineModel;
import com.onyouxi.model.dbModel.WechatUserModel;
import com.onyouxi.model.dbModel.WechatUserPlayModel;
import com.onyouxi.model.pageModel.MachineResult;
import com.onyouxi.model.pageModel.PageResultModel;
import com.onyouxi.model.pageModel.WechatMachineResult;
import com.onyouxi.service.*;
import com.onyouxi.utils.ConfigUtil;
import com.onyouxi.utils.WeixinUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
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

    @Autowired
    private ZhuawawaService zhuawawaService;

    @Autowired
    private ConfigUtil configUtil;

    @RequestMapping(value = "/zhuawawa", method = RequestMethod.GET)
    public String zhuawawa(String code, String machineId, @CookieValue(required = false) String wechatId, Model model, HttpServletResponse response, HttpServletRequest request){
        String openId = WeixinUtil.getUserOpenId(code);
        log.info("openId:"+openId+"   wechatId:"+wechatId);
        if( StringUtils.isEmpty(openId) && StringUtils.isEmpty(wechatId)){
            return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="+configUtil.getAppId()+"&redirect_uri=http://zhua.onyouxi.com/wechat/zhuawawa?machineId="+machineId+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
        }
        WechatUserModel wechatUser = null;
        if(!StringUtils.isEmpty(openId)){
            wechatUser = wechatUserService.findByOpenId(openId);
        }
        if(!StringUtils.isEmpty(wechatId)){
            wechatUser = wechatUserService.findById(wechatId);
        }

        List<WechatUserPlayModel> wechatUserPlayModelList = wechatUserPlayService.findByWechatUserIdAndMachineIdAndStatus(wechatUser.getId(),machineId,0);
        if( null != wechatUserPlayModelList && wechatUserPlayModelList.size() > 0){
            log.info("wechatUserPlayModelList{}" ,wechatUserPlayModelList.size());
            WechatUserPlayModel wechatUserPlayModel = wechatUserPlayModelList.get(0);
            long gameTime = new Date().getTime() - wechatUserPlayModel.getStartTime().getTime();
            log.info("gameTime"+gameTime);
            //游戏时间超时
            if(gameTime > 30*1000 ){
                zhuawawaService.overTime(wechatUserPlayModel.getId());
                startGame(machineId,model,wechatUser);
            }else {
                model.addAttribute("gameTime", 30-(gameTime/1000));
                model.addAttribute("gameStatus", 0);
            }
        }else{
            startGame(machineId,model,wechatUser);
        }

        Cookie cookie = new Cookie("wechatId", wechatUser.getId());
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "wechat/zhuawawa";
    }

    private void startGame(String machineId , Model model , WechatUserModel wechatUser){
        MachineResult machineResult = machineService.findByMachineId(machineId);
        List<WechatMachineResult> wechatMachineResultList = wechatMachineService.findResultByMachineId(machineId);
        model.addAttribute("websocketUrl",websocketUrl);
        model.addAttribute("machine",machineResult);
        model.addAttribute("user",wechatUser);
        model.addAttribute("wechatMachineList",wechatMachineResultList);
        model.addAttribute("gameStatus",1);
        //判断自己是否在已经在排队了
        for(WechatMachineResult wechatMachineResult : wechatMachineResultList){
            if(wechatMachineResult.getWechatUserModel().getId().equals(wechatUser.getId())){
                model.addAttribute("canReserve",0);
            }
        }

    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public String my(String code, @CookieValue(required = false) String wechatId,  Model model, HttpServletResponse response, HttpServletRequest request){
        String openId = WeixinUtil.getUserOpenId(code);
        log.info("openId:"+openId+"   wechatId:"+wechatId);
        if( StringUtils.isEmpty(openId) && StringUtils.isEmpty(wechatId)){
            return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="+configUtil.getAppId()+"&redirect_uri=http://zhua.onyouxi.com/wechat/my?&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
        }
        WechatUserModel wechatUser = null;
        if(!StringUtils.isEmpty(openId)){
            wechatUser = wechatUserService.findByOpenId(openId);
        }
        if(!StringUtils.isEmpty(wechatId)){
            wechatUser = wechatUserService.findById(wechatId);
        }
        model.addAttribute("user",wechatUser);
        Integer pageSize =20;
        Integer pageNum =0;
        PageResultModel pageResultModel = wechatUserPlayService.findPageByWechatUserId(wechatId,pageNum,pageSize);
        model.addAttribute("page",pageResultModel);

        Cookie cookie = new Cookie("wechatId", wechatUser.getId());
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "wechat/my";
    }

}
