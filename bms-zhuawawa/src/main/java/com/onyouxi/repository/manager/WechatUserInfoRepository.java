package com.onyouxi.repository.manager;

import com.onyouxi.model.dbModel.WechatUserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by administrator on 2017/9/22.
 */
@EnableMongoRepositories(mongoTemplateRef = "mongoTemplate")
public interface WechatUserInfoRepository extends MongoRepository<WechatUserInfo,String> {

    WechatUserInfo findByWechatId(String wechatId);

}
