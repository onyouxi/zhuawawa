package com.onyouxi.controller.adminController;

import com.onyouxi.model.dbModel.AdminUserModel;
import com.onyouxi.model.pageModel.PageResultModel;
import com.onyouxi.model.pageModel.RestResultModel;
import com.onyouxi.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuwei on 16/3/2.
 */
@RestController
@RequestMapping(value = "/v1/api/admin/adminUser")
@Slf4j
public class AdminUserController extends BaseAdminController {

    @Autowired
    private AdminUserService adminUserService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(Integer pageNumber,Integer pageSize){
        /**
         if( !"admin".equals(getAdminUser().getUserName()) ){
         throw new IllegalArgumentException("只有管理员才可以使用");
         }
         **/
        pageNumber = pageNumber-1;
        Page<AdminUserModel> adminUserPage = adminUserService.findAll(pageNumber,pageSize);
        PageResultModel pageResultModel = new PageResultModel();
        pageResultModel.setTotal(adminUserPage.getTotalElements());
        pageResultModel.setRows(adminUserPage.getContent());
        return pageResultModel;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResultModel save(String name, String password, String nick){
        /**
        if( !"admin".equals(getAdminUser().getUserName()) ){
            throw new IllegalArgumentException("只有管理员才可以使用");
        }**/
        RestResultModel restResultModel = new RestResultModel();
        adminUserService.createAdminUser(name,password,nick);
        restResultModel.setResult(200);
        return restResultModel;
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public RestResultModel findAdminUser(String nick,String userName){
        RestResultModel restResultModel = new RestResultModel();
        restResultModel.setResult(200);
        if(!StringUtils.isEmpty(nick) && adminUserService.isExistNick(nick)){
            restResultModel.setResult(401);
            restResultModel.setResult_msg("姓名有重复，请重新输入");
        }

        if(!StringUtils.isEmpty(userName) && adminUserService.isExistUserName(userName)){
            restResultModel.setResult(401);
            restResultModel.setResult_msg("账号有重复，请重新输入");
        }

        return restResultModel;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResultModel update(String id,String name, String password, String nick){
        /**
        if( !"admin".equals(getAdminUser().getUserName()) ){
            throw new IllegalArgumentException("只有管理员才可以使用");
        }**/
        RestResultModel restResultModel = new RestResultModel();
        AdminUserModel adminUser = new AdminUserModel();
        adminUser.setId(id);
        adminUser.setUserName(name);
        adminUser.setPassword(password);
        adminUser.setNick(nick);
        adminUserService.updateAdminInfoById(adminUser);
        restResultModel.setResult(200);
        return restResultModel;
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public RestResultModel del(String id){
        /**
        if( !"admin".equals(getAdminUser().getUserName()) ){
            throw new IllegalArgumentException("只有管理员才可以使用");
        }
         **/
        RestResultModel restResultModel = new RestResultModel();
        adminUserService.deleteById(id);
        restResultModel.setResult(200);
        return restResultModel;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public RestResultModel updatePassword(String oldPassword,String newPassword){
        RestResultModel restResultModel = new RestResultModel();
        if(adminUserService.checkPassword(getAdminUser().getId(),oldPassword)){
            AdminUserModel adminUser = new AdminUserModel();
            adminUser.setId(getAdminUser().getId());
            adminUser.setPassword(newPassword);
            adminUserService.updateAdminInfoById(adminUser);
            restResultModel.setResult(200);
        }else{
            restResultModel.setResult(406);
        }

        return restResultModel;
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public RestResultModel findAdminUserById(String id){
        RestResultModel restResultModel = new RestResultModel();
        AdminUserModel adminUser = adminUserService.findById(id);
        restResultModel.setResult(200);
        restResultModel.setData(adminUser);
        return restResultModel;
    }




    @RequestMapping(value = "/testAdd", method = RequestMethod.GET)
    public RestResultModel testAddAdminUser(){
        adminUserService.createAdminUser("liuwei","!QAZ2wsx","刘伟");
        RestResultModel restResultModel = new RestResultModel();
        restResultModel.setData("success");
        return restResultModel;
    }


}
