package com.onyouxi.controller.adminController;

import com.onyouxi.model.dbModel.PrizeModel;
import com.onyouxi.model.pageModel.PageResultModel;
import com.onyouxi.service.PrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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




}
