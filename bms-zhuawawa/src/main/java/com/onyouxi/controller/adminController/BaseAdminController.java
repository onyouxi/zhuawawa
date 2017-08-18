package com.onyouxi.controller.adminController;

import com.onyouxi.model.dbModel.AdminUserModel;

/**
 * Created by administrator on 2017/8/18.
 */
public class BaseAdminController {

    private AdminUserModel adminUser;

    public AdminUserModel getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUserModel adminUser) {
        this.adminUser = adminUser;
    }
}

