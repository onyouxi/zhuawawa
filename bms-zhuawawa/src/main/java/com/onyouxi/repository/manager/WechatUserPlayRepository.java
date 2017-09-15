package com.onyouxi.repository.manager;

import com.onyouxi.model.dbModel.WechatUserPlayModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by administrator on 2017/8/22.
 */
public interface WechatUserPlayRepository extends MongoRepository<WechatUserPlayModel,String> {

    List<WechatUserPlayModel> findByWechatUserId(String wechatUserId);

    List<WechatUserPlayModel> findByMachineId(String machineId);

    List<WechatUserPlayModel> findByWechatUserIdAndMachineId(String wechatUserId,String machineId);

    List<WechatUserPlayModel> findByWechatUserIdAndMachineIdAndStatus(String wechatUserId,String machineId,Integer status);

    List<WechatUserPlayModel> findByStatus(Integer status);

}
