package com.onyouxi.service;

import com.onyouxi.model.dbModel.WechatUserInfo;
import com.onyouxi.repository.manager.WechatUserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by administrator on 2017/9/22.
 */
public class WechatUserInfoService {

    @Autowired
    private WechatUserInfoRepository wechatUserInfoRepository;

    public void save(WechatUserInfo wechatUserInfo){
        wechatUserInfoRepository.insert(wechatUserInfo);
    }

    public WechatUserInfo findById(String id){
        return wechatUserInfoRepository.findOne(id);
    }

    public void update(WechatUserInfo wechatUserInfo){
        if( null != wechatUserInfo){
            WechatUserInfo old = this.findById(wechatUserInfo.getId());
            if( null != old){
                wechatUserInfoRepository.save(wechatUserInfo);
            }
        }
    }

    public WechatUserInfo findByWechatId(String wechatId){
        return wechatUserInfoRepository.findByWechatId(wechatId);
    }


}
