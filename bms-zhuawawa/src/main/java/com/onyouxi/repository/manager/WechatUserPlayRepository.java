package com.onyouxi.repository.manager;

import com.onyouxi.model.dbModel.WechatUserPlayModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

/**
 * Created by administrator on 2017/8/22.
 */
@EnableMongoRepositories(mongoTemplateRef = "mongoTemplate")
public interface WechatUserPlayRepository extends MongoRepository<WechatUserPlayModel,String> {

    List<WechatUserPlayModel> findByWechatUserId(String wechatUserId);

    List<WechatUserPlayModel> findByMachineId(String machineId);

    List<WechatUserPlayModel> findByWechatUserIdAndMachineId(String wechatUserId,String machineId);

    List<WechatUserPlayModel> findByWechatUserIdAndMachineIdAndStatus(String wechatUserId,String machineId,Integer status);

    List<WechatUserPlayModel> findByStatus(Integer status);

    Page<WechatUserPlayModel> findByWechatUserId(String wechatUserId,Pageable pageable);

}
