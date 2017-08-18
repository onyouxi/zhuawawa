package com.onyouxi.controller;

import com.onyouxi.constant.Const;
import com.onyouxi.model.AdminUserModel;
import com.onyouxi.model.RestResultModel;
import com.onyouxi.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by administrator on 2016/11/8.
 */
@RestController
@Slf4j
public class loginController {

    @Autowired
    private AdminUserService adminUserService;

    @RequestMapping(value = "/v1/login" , method = RequestMethod.POST)
    public RestResultModel login(String name,String password,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        RestResultModel restResultModel = new RestResultModel();
        if( StringUtils.isEmpty(name) || StringUtils.isEmpty(password)){
            restResultModel.setResult(503);
            restResultModel.setResult_msg("用户名或者密码不能为空");
            return restResultModel;
        }
        String clientIp = request.getHeader("x-forwarded-for");
        if(StringUtils.isEmpty(clientIp)){
            clientIp = request.getRemoteAddr();
        }
        if(!StringUtils.isEmpty(clientIp) && clientIp.indexOf(",")!=-1){
            clientIp = clientIp.substring(0,clientIp.indexOf(","));
        }
        AdminUserModel adminUser = adminUserService.login(name,password,clientIp,response);
        if( null == adminUser){
            restResultModel.setResult(404);
            restResultModel.setResult_msg("用户名或者密码错误");
            return restResultModel;
        }else{
            restResultModel.setResult(200);

        }
        return restResultModel;
    }


    @RequestMapping(value = "/v1/logout" , method = RequestMethod.GET)
    public RestResultModel logout(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        RestResultModel restResultModel = new RestResultModel();

        adminUserService.logout(request);

        Cookie cookie = new Cookie(Const.COOKIE_AUTH_KEY,"");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        restResultModel.setResult(200);
        return restResultModel;
    }

}
