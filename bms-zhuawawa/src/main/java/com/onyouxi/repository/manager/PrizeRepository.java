package com.onyouxi.repository.manager;

import com.onyouxi.model.dbModel.PrizeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

/**
 * Created by administrator on 2017/8/23.
 */
@EnableMongoRepositories(mongoTemplateRef = "mongoTemplate")
public interface PrizeRepository extends MongoRepository<PrizeModel,String> {

    public List<PrizeModel> findByIdIn(List<String> idList);

}
