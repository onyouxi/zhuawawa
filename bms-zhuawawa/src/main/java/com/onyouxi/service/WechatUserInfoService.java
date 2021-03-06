package com.onyouxi.service;

import com.onyouxi.model.dbModel.WechatUserInfo;
import com.onyouxi.repository.manager.WechatUserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by administrator on 2017/9/22.
 */
@Service
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
            if(StringUtils.isEmpty(wechatUserInfo.getId())){
                wechatUserInfoRepository.save(wechatUserInfo);
            }else{
                WechatUserInfo old = this.findById(wechatUserInfo.getId());
                if( null != old){
                    wechatUserInfoRepository.save(wechatUserInfo);
                }
            }
        }
    }

    public WechatUserInfo findByWechatId(String wechatId){
        return wechatUserInfoRepository.findByWechatId(wechatId);
    }


}
