package com.onyouxi.repository.manager;

import com.onyouxi.model.dbModel.WechatReward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by administrator on 2016/11/29.
 */
@EnableMongoRepositories(mongoTemplateRef = "mongoTemplate")
public interface WechatRewardRepository extends MongoRepository<WechatReward,String> {

    Page<WechatReward> findAll(Pageable pageable);

    Page<WechatReward> findAll(String wechatId,Pageable pageable);

}
