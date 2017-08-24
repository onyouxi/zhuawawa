package com.onyouxi.controller.adminController;

import com.onyouxi.model.dbModel.MachineModel;
import com.onyouxi.model.pageModel.PageResultModel;
import com.onyouxi.model.pageModel.RestResultModel;
import com.onyouxi.service.MachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by administrator on 2017/8/21.
 */
@RestController
@RequestMapping(value = "/v1/api/admin/machine")
@Slf4j
public class MachineController {

    @Autowired
    private MachineService machineService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(Integer pageNumber, Integer pageSize){
        pageNumber = pageNumber-1;
        return machineService.findAllMachine(pageNumber,pageSize);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResultModel save(@ModelAttribute MachineModel machineModel){
        RestResultModel restResultModel = new RestResultModel();
        machineService.save(machineModel);
        restResultModel.setResult(200);
        return restResultModel;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResultModel update(@ModelAttribute MachineModel machineModel){
        RestResultModel restResultModel = new RestResultModel();
        machineService.update(machineModel);
        restResultModel.setResult(200);
        return restResultModel;
    }

    @RequestMapping(value = "/updateCanUse", method = RequestMethod.GET)
    public RestResultModel updateCanUse(String id,Integer canUse){
        RestResultModel restResultModel = new RestResultModel();
        machineService.updateCanUse(id,canUse);
        restResultModel.setResult(200);
        return restResultModel;
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public RestResultModel del(String id){
        RestResultModel restResultModel = new RestResultModel();
        machineService.del(id);
        restResultModel.setResult(200);
        return restResultModel;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public RestResultModel findById(String id){
        RestResultModel restResultModel = new RestResultModel();
        MachineModel machineModel = machineService.findById(id);
        restResultModel.setResult(200);
        restResultModel.setData(machineModel);
        return restResultModel;
    }

    @RequestMapping(value = "/selectPrize", method = RequestMethod.GET)
    public RestResultModel selectPrize(String id,String prizeId){
        RestResultModel restResultModel = new RestResultModel();
        machineService.updatePrizeId(id,prizeId);
        restResultModel.setResult(200);
        return restResultModel;
    }



}
