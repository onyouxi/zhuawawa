package com.onyouxi.repository.manager;

import com.onyouxi.model.dbModel.WechatUserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

/**
 * Created by liuwei on 16/7/21.
 */

public interface WechatUserRepository extends MongoRepository<WechatUserModel, String> {

    public WechatUserModel findByOpenId(String openId);

    public Page<WechatUserModel> findAll(Pageable pageable);

    public Page<WechatUserModel> findByNickLike(String nick,Pageable pageable);

    public long countBySubscribeTimeBetween(long from, long to);

    public Page<WechatUserModel> findBySubscribeTimeBetween(long from, long to,Pageable pageable);

    public List<WechatUserModel> findByIdIn(List<String> idList);

}
