package com.onyouxi.model.pageModel;

import com.onyouxi.model.dbModel.MachineModel;
import com.onyouxi.model.dbModel.WechatUserModel;

/**
 * Created by administrator on 2017/8/22.
 */
public class WechatMachineResult {

    private WechatUserModel wechatUserModel;

    private MachineModel machineModel;

    public WechatUserModel getWechatUserModel() {
        return wechatUserModel;
    }

    public void setWechatUserModel(WechatUserModel wechatUserModel) {
        this.wechatUserModel = wechatUserModel;
    }

    public MachineModel getMachineModel() {
        return machineModel;
    }

    public void setMachineModel(MachineModel machineModel) {
        this.machineModel = machineModel;
    }
}
