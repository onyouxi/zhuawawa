package com.onyouxi.service;

import com.onyouxi.model.AdminUserModel;
import com.onyouxi.repository.manager.AdminUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuwei on 16/3/1.
 */
@Service
@Slf4j
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Resource(name="adminUserMongoTemplate")
    private MongoOperations mongoOperation;


    public AdminUserModel save(AdminUserModel adminUserModel){

        AdminUserModel adminUser = adminUserRepository.findByUserName(adminUserModel.getUserName());
        if( null != adminUser){
            throw new IllegalArgumentException("用户名已经存在");
        }

        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
        //String hashedPassword = passwordEncoder.encode(adminUserModel.getPassword());
        //adminUserModel.setPassword(hashedPassword);
        return  adminUserRepository.save(adminUserModel);
    }

    public void deleteByUserName(String userName){
        AdminUserModel adminUserModel = this.findByUserName(userName);
        adminUserRepository.delete(adminUserModel);
    }

    public AdminUserModel updateById(AdminUserModel adminUserModel){

        return null;
    }

    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    public AdminUserModel login(String userName , String password){
        return adminUserRepository.findByUserNameAndPassword(userName, password);
    }


    public AdminUserModel findByUserName(String userName){
        return adminUserRepository.findByUserName(userName);
    }


    public Page<AdminUserModel> findAll(int page,int size){
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        PageRequest pageRequest = new PageRequest(page,size,sort);
        return adminUserRepository.findAll(pageRequest);
    }

    public List<AdminUserModel> findByIds(List<String> ids){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(ids));
        return mongoOperation.find(query, AdminUserModel.class);
    }


}
