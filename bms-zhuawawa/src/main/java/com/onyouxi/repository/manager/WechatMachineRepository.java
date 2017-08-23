package com.onyouxi.repository.manager;

import com.onyouxi.model.dbModel.WechatMachineModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by administrator on 2017/8/22.
 */
public interface WechatMachineRepository extends MongoRepository<WechatMachineModel,String> {

    List<WechatMachineModel> findByMachineId(String machineId,Sort sort);

    WechatMachineModel findByWechatId(String wechatId);

    List<WechatMachineModel> findByWechatIdAndMachineId(String wechatId,String machineId);


}
