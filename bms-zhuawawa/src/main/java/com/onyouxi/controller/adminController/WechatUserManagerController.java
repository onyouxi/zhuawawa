package com.onyouxi.controller.adminController;

import com.onyouxi.model.dbModel.WechatUserModel;
import com.onyouxi.model.pageModel.PageResultModel;
import com.onyouxi.model.pageModel.RestResultModel;
import com.onyouxi.service.WechatUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by administrator on 2016/11/28.
 */

@RestController
@RequestMapping(value = "/v1/api/admin/wechatmanager")
@Slf4j
public class WechatUserManagerController {

    @Autowired
    private WechatUserService wechatUserService;


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(String nick , int pageNumber,int pageSize){
        pageNumber = pageNumber-1;
        Page<WechatUserModel> page = null;
        if(StringUtils.isEmpty(nick)){
            page = wechatUserService.findAll(pageNumber,pageSize);
        }else{
            page = wechatUserService.findByNickLike(nick,pageNumber,pageSize);
        }

        return new PageResultModel(page);

    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public RestResultModel delById(String id){
        RestResultModel restResultModel = new RestResultModel();
        wechatUserService.delById(id);
        restResultModel.setResult(200);
        return restResultModel;
    }


}
