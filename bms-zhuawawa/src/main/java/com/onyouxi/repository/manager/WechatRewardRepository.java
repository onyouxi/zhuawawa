package com.onyouxi.repository.manager;

import com.onyouxi.model.dbModel.WechatReward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by administrator on 2016/11/29.
 */
public interface WechatRewardRepository extends MongoRepository<WechatReward,String> {

    Page<WechatReward> findAll(Pageable pageable);

}
