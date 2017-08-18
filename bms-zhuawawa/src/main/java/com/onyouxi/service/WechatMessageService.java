package com.onyouxi.service;

import com.onyouxi.model.dbModel.WechatMessage;
import com.onyouxi.repository.manager.WechatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by liuwei on 2016/9/23.
 */
@Service
public class WechatMessageService {

    @Autowired
    private WechatMessageRepository wechatMessageRepository;

    public WechatMessage save(String words , String message,String mediaId,String mediaName){
        WechatMessage weixinMessage = new WechatMessage();
        weixinMessage.setMessage(message);
        weixinMessage.setWords(words);
        weixinMessage.setMediaId(mediaId);
        weixinMessage.setMediaName(mediaName);
        return wechatMessageRepository.save(weixinMessage);
    }

    public void save(List<WechatMessage> weixinMessageList){
        wechatMessageRepository.save(weixinMessageList);
    }

    public void del(String id){
        wechatMessageRepository.delete(id);
    }

    public void update(String id,String words,String message){
        WechatMessage wechatMessage = wechatMessageRepository.findOne(id);
        if( null == wechatMessage){
            throw new IllegalArgumentException("更新的自动回复不存在");
        }
        wechatMessage.setMessage(words);
        wechatMessage.setMessage(message);
        wechatMessageRepository.save(wechatMessage);
    }

    public void saveOrupdate(List<WechatMessage> weixinMessageList){
        for(WechatMessage weixinMessage : weixinMessageList){
            if(StringUtils.isEmpty(weixinMessage.getId())){
                weixinMessage.setId(null);
                wechatMessageRepository.insert(weixinMessage);
            }else{
                wechatMessageRepository.save(weixinMessage);
            }
        }
    }

    public List<WechatMessage> findAll(){
        return wechatMessageRepository.findAll();
    }

    public Page<WechatMessage> findAll(int page , int size){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        return wechatMessageRepository.findAll(pageRequest);
    }

    public Page<WechatMessage> findByWordsLike(String words, int page , int size){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        return wechatMessageRepository.findByWordsLike(words,pageRequest);

    }

    public WechatMessage findById(String id){

        return wechatMessageRepository.findOne(id);
    }

    public List<WechatMessage> findByWords(String words){
        return wechatMessageRepository.findByWords(words);
    }


    public void updateWeixinMessageVoice(String id,String mediaId,String mediaName){
        WechatMessage weixinMessage = this.findById(id);
        if( null != weixinMessage){
            weixinMessage.setMediaId(mediaId);
            weixinMessage.setMediaName(mediaName);
        }
        wechatMessageRepository.save(weixinMessage);
    }

    public void cancelWeixinMessageVoice(String id){
        WechatMessage weixinMessage = this.findById(id);
        if( null != weixinMessage){
            weixinMessage.setMediaId(null);
            weixinMessage.setMediaName(null);
        }
        wechatMessageRepository.save(weixinMessage);
    }


}
