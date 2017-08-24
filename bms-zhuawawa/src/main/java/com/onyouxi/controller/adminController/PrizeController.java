package com.onyouxi.controller.adminController;

import com.onyouxi.model.dbModel.PrizeModel;
import com.onyouxi.model.pageModel.PageResultModel;
import com.onyouxi.model.pageModel.RestResultModel;
import com.onyouxi.service.PrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by administrator on 2017/8/23.
 */
@RestController
@RequestMapping(value = "/v1/api/admin/prize")
@Slf4j
public class PrizeController {

    @Autowired
    private PrizeService prizeService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(Integer pageNumber, Integer pageSize){
        pageNumber = pageNumber-1;
        Page<PrizeModel> prizeModelPage = prizeService.findAll(pageNumber,pageSize);
        return new PageResultModel(prizeModelPage);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResultModel save(@ModelAttribute PrizeModel prizeModel){
        RestResultModel restResultModel = new RestResultModel();
        prizeService.save(prizeModel);
        restResultModel.setResult(200);
        return restResultModel;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResultModel update(@ModelAttribute PrizeModel prizeModel){
        RestResultModel restResultModel = new RestResultModel();
        prizeService.update(prizeModel);
        restResultModel.setResult(200);
        return restResultModel;
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public RestResultModel del(String id){
        RestResultModel restResultModel = new RestResultModel();
        prizeService.del(id);
        restResultModel.setResult(200);
        return restResultModel;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public RestResultModel get(String id){
        RestResultModel restResultModel = new RestResultModel();
        PrizeModel prizeModel = prizeService.findById(id);
        restResultModel.setResult(200);
        restResultModel.setData(prizeModel);
        return restResultModel;
    }





}
