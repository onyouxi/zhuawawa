package com.onyouxi.controller;

import com.onyouxi.model.AdminUserModel;
import com.onyouxi.model.RestResultModel;
import com.onyouxi.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwei on 16/3/2.
 */
@RestController
@RequestMapping(value = "/v1/adminUser")
@Slf4j
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RestResultModel findAllAdminUser(){
        Page<AdminUserModel> adminUsers = adminUserService.findAll(0,10);
        RestResultModel restResultModel = new RestResultModel();
        restResultModel.setData(adminUsers);
        return restResultModel;
    }


    @RequestMapping(value = "/testAdd", method = RequestMethod.GET)
    public RestResultModel testAddAdminUser(){
        adminUserService.createAdminUser("liuwei","!QAZ2wsx","刘伟");
        RestResultModel restResultModel = new RestResultModel();
        restResultModel.setData("success");
        return restResultModel;
    }


}
