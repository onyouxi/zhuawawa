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
@RequestMapping(value = "/adminUser")
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

    @RequestMapping(value = "/testFind", method = RequestMethod.GET)
    public RestResultModel testFindAdminUser(){
        List<String> ids = new ArrayList<String>();
        ids.add("5768da13bee8e17853efc591");
        ids.add("5768da13bee8e17853efc593");
        List<AdminUserModel> adminUserModelList = adminUserService.findByIds(ids);
        RestResultModel restResultModel = new RestResultModel();
        restResultModel.setData(adminUserModelList);
        return restResultModel;
    }


    @RequestMapping(value = "/testAdd", method = RequestMethod.GET)
    public RestResultModel testAddAdminUser(){
        List<AdminUserModel> adminUserModelList = new ArrayList<AdminUserModel>();
        for(int i=0;i<10;i++){
            AdminUserModel adminUserModel = new AdminUserModel();
            adminUserModel.setPassword("test" + i);
            adminUserModel.setUserName("test" + i);
            adminUserModel.setNick("test" + i);
            adminUserService.save(adminUserModel);
        }

        RestResultModel restResultModel = new RestResultModel();
        restResultModel.setData(adminUserModelList);
        return restResultModel;
    }


}
