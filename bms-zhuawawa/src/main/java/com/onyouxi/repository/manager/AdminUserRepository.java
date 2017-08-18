package com.onyouxi.repository.manager;

import com.onyouxi.model.AdminUserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by liuwei on 16/3/1.
 */
@EnableMongoRepositories(mongoTemplateRef = "mongoTemplate")
public interface AdminUserRepository extends MongoRepository<AdminUserModel, String> {

     AdminUserModel findByUserName(String userName);

     AdminUserModel findByNick(String nick);

     AdminUserModel findByUserNameAndPassword(String userName,String password);

     Page<AdminUserModel> findAll(Pageable pageable);


}
