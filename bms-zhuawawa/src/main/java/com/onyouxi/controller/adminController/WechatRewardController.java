package com.onyouxi.controller.adminController;

import com.onyouxi.model.pageModel.PageResultModel;
import com.onyouxi.service.WechatRewardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by administrator on 2016/11/29.
 */
@RestController
@RequestMapping(value = "/v1/api/admin/reward")
@Slf4j
public class WechatRewardController {

    @Autowired
    private WechatRewardService wechatRewardService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(Integer pageNumber, Integer pageSize){
        pageNumber = pageNumber-1;
        return wechatRewardService.findPage(pageNumber,pageSize);

    }



}
