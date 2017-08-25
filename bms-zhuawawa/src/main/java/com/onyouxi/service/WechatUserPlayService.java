package com.onyouxi.service;

import com.onyouxi.model.dbModel.WechatUserPlayModel;
import com.onyouxi.repository.manager.WechatUserPlayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by administrator on 2017/8/22.
 */
@Service
@Slf4j
public class WechatUserPlayService {

    @Autowired
    private WechatUserPlayRepository wechatUserPlayRepository;

    public WechatUserPlayModel save(WechatUserPlayModel wechatUserPlayModel){
        return wechatUserPlayRepository.save(wechatUserPlayModel);
    }

    public void updateStatus(String id,Integer status,String prizeId){
        WechatUserPlayModel wechatUserPlayModel = this.findById(id);
        if( null != wechatUserPlayModel){
            wechatUserPlayModel.setEndTime(new Date());
            wechatUserPlayModel.setStatus(status);
            wechatUserPlayModel.setPrizeId(prizeId);
            wechatUserPlayRepository.save(wechatUserPlayModel);
        }
    }

    public void updateEndTime(String id){
        WechatUserPlayModel wechatUserPlayModel = this.findById(id);
        if( null != wechatUserPlayModel){
            wechatUserPlayModel.setEndTime(new Date());
            wechatUserPlayRepository.save(wechatUserPlayModel);
        }
    }

    public WechatUserPlayModel findById(String id){
        return wechatUserPlayRepository.findOne(id);
    }

    public List<WechatUserPlayModel> findByWechatUserId(String wechatUserId){
        return wechatUserPlayRepository.findByWechatUserId(wechatUserId);
    }

    public List<WechatUserPlayModel> findByMachineId(String machineId){
        return wechatUserPlayRepository.findByMachineId(machineId);
    }

    public List<WechatUserPlayModel> findByWechatUserIdAndMachineId(String wechatUserId,String machineId){
        return wechatUserPlayRepository.findByWechatUserIdAndMachineId(wechatUserId,machineId);
    }

    public List<WechatUserPlayModel> findByWechatUserIdAndMachineIdAndStatus(String wechatUserId,String machineId,Integer status){
        return wechatUserPlayRepository.findByWechatUserIdAndMachineIdAndStatus(wechatUserId,machineId,status);
    }


}
