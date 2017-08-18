package com.onyouxi.controller.adminController;

import com.onyouxi.model.dbModel.WechatMessage;
import com.onyouxi.model.pageModel.PageResultModel;
import com.onyouxi.model.pageModel.RestResultModel;
import com.onyouxi.service.WechatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;


/**
 * Created by liuwei on 2016/9/23.
 */

@RestController
@RequestMapping(value = "/v1/api/admin/wxmessage")
@Slf4j
public class WechatMessageController {

    @Autowired
    private WechatMessageService wechatMessageService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel weixinMessageList(String words , int pageNumber, int pageSize){
        pageNumber = pageNumber-1;
        Page<WechatMessage> wechatMessagePage = null;
        if(StringUtils.isEmpty(words)){
            wechatMessagePage = wechatMessageService.findAll(pageNumber,pageSize);
        }else{
            wechatMessagePage = wechatMessageService.findByWordsLike(words,pageNumber,pageSize);
        }
        return new PageResultModel(wechatMessagePage);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public RestResultModel del(String id){
        RestResultModel restResultModel = new RestResultModel();
        wechatMessageService.del(id);
        restResultModel.setResult(200);
        return restResultModel;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResultModel save(String words,String message,String mediaId,String mediaName){
        RestResultModel restResultModel = new RestResultModel();
        wechatMessageService.save(words,message,mediaId,mediaName);
        restResultModel.setResult(200);
        return restResultModel;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResultModel update(String id,String words,String message){
        RestResultModel restResultModel = new RestResultModel();
        wechatMessageService.update(id,words,message);
        restResultModel.setResult(200);
        return restResultModel;
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public RestResultModel findById(String id){
        RestResultModel restResultModel = new RestResultModel();
        WechatMessage wechatMessage = wechatMessageService.findById(id);
        restResultModel.setResult(200);
        restResultModel.setData(wechatMessage);
        return restResultModel;
    }

    @RequestMapping(value = "/findByWords", method = RequestMethod.GET)
    public RestResultModel findByWords(String words){
        RestResultModel restResultModel = new RestResultModel();
        List<WechatMessage> wechatMessageList = wechatMessageService.findByWords(words);
        restResultModel.setResult(200);
        restResultModel.setData(wechatMessageList.size());
        return restResultModel;
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST , consumes = "application/json")
    public RestResultModel saveOrUpdate(@RequestBody(required = false) List<WechatMessage> weixinMessageList ){
        RestResultModel restResultModel = new RestResultModel();
        if( null != weixinMessageList && weixinMessageList.size() > 0){
            for(WechatMessage weixinMessage : weixinMessageList){
                try {
                    weixinMessage.setWords(URLDecoder.decode(weixinMessage.getWords(),"utf-8"));
                    weixinMessage.setMessage(URLDecoder.decode(weixinMessage.getMessage(),"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            wechatMessageService.saveOrupdate(weixinMessageList);
        }
        restResultModel.setResult(200);
        return restResultModel;
    }

}
