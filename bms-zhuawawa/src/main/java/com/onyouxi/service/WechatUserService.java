package com.onyouxi.service;

import com.onyouxi.model.dbModel.WechatUserModel;
import com.onyouxi.repository.manager.WechatUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by liuwei on 2016/9/28.
 */

@Service
@Slf4j
public class WechatUserService {

    @Autowired
    private WechatUserRepository wechatUserRepository;

    @Autowired
    private WechatUserLogService wechatUserLogService;

    public WechatUserModel findById(String id){
        return wechatUserRepository.findOne(id);
    }

    public WechatUserModel save(WechatUserModel wechatUser) {
        WechatUserModel oldWebchatUser = wechatUserRepository.findByOpenId(wechatUser.getOpenId());
        if (null != oldWebchatUser) {
            oldWebchatUser.setCity(wechatUser.getCity());
            oldWebchatUser.setProvince(wechatUser.getProvince());
            oldWebchatUser.setSex(wechatUser.getSex());
            oldWebchatUser.setCountry(wechatUser.getCountry());
            oldWebchatUser.setImgUrl(wechatUser.getImgUrl());
            oldWebchatUser.setLanguage(wechatUser.getLanguage());
            oldWebchatUser.setNick(wechatUser.getNick());
            oldWebchatUser.setLastOpenDate(new Date());
            if (null != wechatUser.getSubscribeState()) {
                oldWebchatUser.setSubscribeState(wechatUser.getSubscribeState());
            }
            return wechatUserRepository.save(oldWebchatUser);
        } else {
            //保存用户信息
            wechatUser.setLastOpenDate(new Date());
            wechatUser.setSubscribeState(0);
            //保存微信用户信息
            return wechatUserRepository.insert(wechatUser);
        }
    }

    public WechatUserModel updateUserInfo(WechatUserModel wechatUser) {
        WechatUserModel oldWebchatUser = wechatUserRepository.findByOpenId(wechatUser.getOpenId());
        if (null != oldWebchatUser) {
            oldWebchatUser.setCity(wechatUser.getCity());
            oldWebchatUser.setProvince(wechatUser.getProvince());
            oldWebchatUser.setSex(wechatUser.getSex());
            oldWebchatUser.setCountry(wechatUser.getCountry());
            oldWebchatUser.setImgUrl(wechatUser.getImgUrl());
            oldWebchatUser.setLanguage(wechatUser.getLanguage());
            oldWebchatUser.setNick(wechatUser.getNick());
            oldWebchatUser.setLastOpenDate(new Date());
            return wechatUserRepository.save(oldWebchatUser);
        } else {
            return null;
        }
    }

    public void delByOpenId(String openId) {
        WechatUserModel wechatUser = wechatUserRepository.findByOpenId(openId);
        if (null != wechatUser) {
            wechatUserRepository.delete(wechatUser.getId());
        }
    }

    public void delById(String id){
        wechatUserRepository.delete(id);
    }


    public WechatUserModel findByOpenId(String openId) {

        return wechatUserRepository.findByOpenId(openId);
    }

    public Page<WechatUserModel> findAll(Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "subscribeTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        return wechatUserRepository.findAll(pageRequest);
    }

    public Page<WechatUserModel> findByNickLike(String nick, Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "subscribeTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        return wechatUserRepository.findByNickLike(nick, pageRequest);
    }

    public void unsubscribe(String openId) {
        WechatUserModel wechatUser = this.findByOpenId(openId);
        wechatUser.setSubscribeState(1);
        wechatUserRepository.save(wechatUser);
        wechatUserLogService.save(openId, "unsubscribe");
    }


    public long countBySubscribeTimeBetween(long from,long to){
        return wechatUserRepository.countBySubscribeTimeBetween(from,to);
    }
    public Page<WechatUserModel> findBySubscribeTimeBetween(long from,long to,int page,int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "subscribeTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        return wechatUserRepository.findBySubscribeTimeBetween(from,to, pageRequest);
    }



    public List<WechatUserModel> findAll(){
        return wechatUserRepository.findAll();
    }



}