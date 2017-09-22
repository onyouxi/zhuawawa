package com.onyouxi.repository.manager;

import com.onyouxi.model.dbModel.WechatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

/**
 * Created by liuwei on 2016/9/23.
 */
@EnableMongoRepositories(mongoTemplateRef = "mongoTemplate")
public interface WechatMessageRepository extends MongoRepository<WechatMessage,String> {

    Page<WechatMessage> findAll(Pageable pageable);

    Page<WechatMessage> findByWordsLike(String words,Pageable pageable);

    List<WechatMessage> findByWords(String words);

}
