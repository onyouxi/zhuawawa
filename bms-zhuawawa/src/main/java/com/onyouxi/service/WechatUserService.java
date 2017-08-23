package com.onyouxi.service;

import com.onyouxi.model.dbModel.WechatUserModel;
import com.onyouxi.model.pageModel.AccessTokenCacheModel;
import com.onyouxi.repository.manager.WechatUserRepository;
import com.onyouxi.utils.WeixinUtil;
import com.onyouxi.wechat.pojo.AccessToken;
import com.onyouxi.wechat.pojo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

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


    private List<AccessTokenCacheModel> accessTokenCache = Collections.synchronizedList(new ArrayList<>());

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
            oldWebchatUser.setSubscribeState(0);
            if (null != wechatUser.getSubscribeState()) {
                oldWebchatUser.setSubscribeState(wechatUser.getSubscribeState());
            }
            return wechatUserRepository.save(oldWebchatUser);
        } else {
            //保存用户信息
            wechatUser.setLastOpenDate(new Date());
            wechatUser.setSubscribeState(0);
            //首次关注送5次游戏次数
            wechatUser.setPlayNum(5);
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
        if(StringUtils.isEmpty(openId)){
            return null;
        }
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
        if( null != wechatUser){
            wechatUser.setSubscribeState(1);
            wechatUserRepository.save(wechatUser);
            wechatUserLogService.save(openId, "unsubscribe");
        }

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

    public AccessToken getAccessToken(){
        log.info("get access token");
        AccessToken accessToken = null;
        AccessTokenCacheModel accessTokenCacheModel = null;
        if(accessTokenCache.size() > 0){
            accessTokenCacheModel = accessTokenCache.get(0);
        }
        if( null !=  accessTokenCacheModel) {
            long a = new Date().getTime() - accessTokenCacheModel.getCreateTime().getTime();
            if( a > 2*3600*1000 ){
                accessToken = new WeixinUtil().getAccessToken();
                accessTokenCacheModel = new AccessTokenCacheModel();
                accessTokenCacheModel.setAccessToken(accessToken);
                accessTokenCacheModel.setCreateTime(new Date());
                accessTokenCache.add(accessTokenCacheModel);
            }else{
                accessToken = accessTokenCacheModel.getAccessToken();
            }
        }else{
            accessToken = WeixinUtil.getAccessToken();
            accessTokenCacheModel = new AccessTokenCacheModel();
            accessTokenCacheModel.setAccessToken(accessToken);
            accessTokenCacheModel.setCreateTime(new Date());
            accessTokenCache.add(accessTokenCacheModel);
        }
        return accessToken;
    }

    public WechatUserModel save(String openId){
        UserInfo userInfo =  WeixinUtil.getUserInfo(this.getAccessToken().getToken(),openId);
        if( null != userInfo){
            return this.save(userInfo.toWechatUser());
        }else{
            return null;
        }
    }

    public void updateWechatUserPlayNum(String wechatId,Integer num){
        WechatUserModel wechatUserModel = this.findById(wechatId);
        if( null != wechatUserModel){
            if( null == wechatUserModel.getPlayNum() || wechatUserModel.getPlayNum() < 0){
                wechatUserModel.setPlayNum(0);
            }
            wechatUserModel.setPlayNum(wechatUserModel.getPlayNum()+num);
            wechatUserRepository.save(wechatUserModel);
        }
    }


}
