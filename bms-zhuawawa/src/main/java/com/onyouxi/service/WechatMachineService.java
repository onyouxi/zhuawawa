package com.onyouxi.service;

import com.onyouxi.model.dbModel.WechatMachineModel;
import com.onyouxi.model.dbModel.WechatUserModel;
import com.onyouxi.model.pageModel.WechatMachineResult;
import com.onyouxi.repository.manager.MachineRepository;
import com.onyouxi.repository.manager.WechatMachineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 2017/8/22.
 */
@Service
@Slf4j
public class WechatMachineService {

    @Autowired
    private WechatMachineRepository wechatMachineRepository;

    @Autowired
    private WechatUserService wechatUserService;

    public WechatMachineModel save(WechatMachineModel wechatMachineModel){
        return wechatMachineRepository.save(wechatMachineModel);
    }

    public List<WechatMachineModel> findByMachineId(String machineId){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return wechatMachineRepository.findByMachineId(machineId,sort);
    }

    public WechatMachineModel findByWechatId(String wechatId){
        return wechatMachineRepository.findByWechatId(wechatId);
    }

    public void del(String id){
        wechatMachineRepository.delete(id);
    }


    public List<WechatMachineResult> findResultByMachineId(String machineId){
        List<WechatMachineResult> wechatMachineResultList = new ArrayList<>();
        List<WechatMachineModel> wechatMachineModelList = this.findByMachineId(machineId);
        if( null != wechatMachineModelList && wechatMachineModelList.size() > 0){
            List<String> wechatIdList = new ArrayList<>();
            for(WechatMachineModel wechatMachineModel : wechatMachineModelList){
                wechatIdList.add(wechatMachineModel.getWechatId());
            }
            List<WechatUserModel> wechatUserModelList = wechatUserService.findByIds(wechatIdList);

            for(WechatMachineModel wechatMachineModel : wechatMachineModelList){
                WechatMachineResult wechatMachineResult = new WechatMachineResult();
                for(WechatUserModel wechatUserModel :wechatUserModelList){
                    if(!StringUtils.isEmpty(wechatMachineModel.getWechatId()) && wechatMachineModel.getWechatId().equals(wechatUserModel.getId())){
                        wechatMachineResult.setWechatUserModel(wechatUserModel);
                    }
                }
                wechatMachineResultList.add(wechatMachineResult);
            }

        }
        return wechatMachineResultList;
    }


}
