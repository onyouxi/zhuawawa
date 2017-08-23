package com.onyouxi.service;

import com.onyouxi.model.dbModel.WechatMachineModel;
import com.onyouxi.repository.manager.MachineRepository;
import com.onyouxi.repository.manager.WechatMachineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by administrator on 2017/8/22.
 */
@Service
@Slf4j
public class WechatMachineService {

    @Autowired
    private WechatMachineRepository wechatMachineRepository;

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


}
