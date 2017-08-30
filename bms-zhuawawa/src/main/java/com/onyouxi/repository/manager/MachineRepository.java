package com.onyouxi.repository.manager;

import com.onyouxi.model.dbModel.MachineModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by administrator on 2017/8/21.
 */
@EnableMongoRepositories(mongoTemplateRef = "mongoTemplate")
public interface MachineRepository extends MongoRepository<MachineModel,String> {

    MachineModel findByCode(String code);
}
