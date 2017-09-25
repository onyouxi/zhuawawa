package com.onyouxi.service;

import com.onyouxi.model.dbModel.PrizeModel;
import com.onyouxi.model.dbModel.WechatUserPlayModel;
import com.onyouxi.model.pageModel.PageResultModel;
import com.onyouxi.model.pageModel.WechatPlayResult;
import com.onyouxi.repository.manager.WechatUserPlayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

    @Autowired
    private PrizeService prizeService;

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

    public List<WechatUserPlayModel> findAll(){
        return wechatUserPlayRepository.findAll();
    }

    public List<WechatUserPlayModel> findByStatus(Integer status){
        return wechatUserPlayRepository.findByStatus(status);
    }

    public Page<WechatUserPlayModel> findByWechatUserId(String wechatUserId,int pageNum,int pageSize){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(pageNum, pageSize, sort);
        return wechatUserPlayRepository.findByWechatUserId(wechatUserId,pageRequest);
    }


    public PageResultModel findPageByWechatUserId(String wechatUserId,int pageNum,int pageSize){
        PageResultModel pageResultModel = new PageResultModel();
        Page<WechatUserPlayModel> wechatUserPlayModelPage = findByWechatUserId(wechatUserId,pageNum,pageSize);
        if( null != wechatUserPlayModelPage){
            List<String> prizeIdList = new ArrayList<String>();
            for(WechatUserPlayModel wechatUserPlayModel : wechatUserPlayModelPage.getContent()){
                prizeIdList.add(wechatUserPlayModel.getPrizeId());
            }
            List<PrizeModel> prizeModelList = prizeService.findByIds(prizeIdList);
            List<WechatPlayResult> wechatPlayResultList = new ArrayList<>();
            for(WechatUserPlayModel wechatUserPlayModel : wechatUserPlayModelPage.getContent()){
                WechatPlayResult wechatPlayResult = new WechatPlayResult();
                wechatPlayResult.setWechatUserPlayModel(wechatUserPlayModel);
                if(!StringUtils.isEmpty(wechatUserPlayModel.getPrizeId()) && null != prizeModelList){
                    for(PrizeModel prizeModel : prizeModelList){
                        if(prizeModel.getId().equals(wechatUserPlayModel.getPrizeId())){
                            wechatPlayResult.setPrizeModel(prizeModel);
                        }
                    }
                }
                wechatPlayResultList.add(wechatPlayResult);
            }
            pageResultModel.setRows(wechatPlayResultList);
            pageResultModel.setTotal(wechatUserPlayModelPage.getTotalElements());
        }
        return pageResultModel;
    }


}
