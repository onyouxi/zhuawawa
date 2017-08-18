package com.onyouxi.service;

import com.onyouxi.model.dbModel.WechatReward;
import com.onyouxi.model.pageModel.PageResultModel;
import com.onyouxi.model.pageModel.WechatRewardListModel;
import com.onyouxi.repository.manager.WechatRewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 2016/11/29.
 */
@Service
public class WechatRewardService {

    @Autowired
    private WechatRewardRepository wechatRewardRepository;



    public WechatReward save(String machineId , String openId, String timestamp, Integer total_fee){
        WechatReward wechatReward = new WechatReward();
        wechatReward.setMachineId(machineId);
        wechatReward.setOpenId(openId);
        wechatReward.setTimestamp(timestamp);
        wechatReward.setTotal_fee(total_fee);
        return wechatRewardRepository.save(wechatReward);
    }

    public Page<WechatReward> findAll(int page , int size){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        return wechatRewardRepository.findAll(pageRequest);
    }

    public PageResultModel<WechatRewardListModel> findPage(int page , int size){
        PageResultModel pageResultModel = new PageResultModel();
        Page<WechatReward> wechatRewardPage = this.findAll(page,size);
        if( null != wechatRewardPage && null != wechatRewardPage.getContent()) {
            List<WechatRewardListModel> wechatRewardResultList = new ArrayList<>();
            for (WechatReward wechatReward : wechatRewardPage.getContent()) {
                WechatRewardListModel wechatRewardResult = new WechatRewardListModel();
                wechatRewardResult.setWechatReward(wechatReward);
            }
            pageResultModel.setRows(wechatRewardResultList);
            pageResultModel.setTotal(wechatRewardPage.getTotalElements());

        }

        return pageResultModel;

    }


}
