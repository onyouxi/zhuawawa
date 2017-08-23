package com.onyouxi.model.pageModel;

import com.onyouxi.model.dbModel.MachineModel;
import com.onyouxi.model.dbModel.WechatUserModel;

/**
 * Created by administrator on 2017/8/23.
 */
public class MachineResult {

    private MachineModel machineModel;

    private WechatUserModel wechatUserModel;

    public MachineModel getMachineModel() {
        return machineModel;
    }

    public void setMachineModel(MachineModel machineModel) {
        this.machineModel = machineModel;
    }

    public WechatUserModel getWechatUserModel() {
        return wechatUserModel;
    }

    public void setWechatUserModel(WechatUserModel wechatUserModel) {
        this.wechatUserModel = wechatUserModel;
    }
}
