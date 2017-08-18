package com.onyouxi.repository.manager;

import com.onyouxi.model.dbModel.WechatUserLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by administrator on 2016/11/30.
 */
@EnableMongoRepositories(mongoTemplateRef = "userMongoTemplate")
public interface WechatUserLogRepository extends MongoRepository<WechatUserLog,String> {


}
