package com.onyouxi.model.pageModel;

import com.onyouxi.model.dbModel.MachineModel;
import com.onyouxi.model.dbModel.WechatReward;

/**
 * Created by administrator on 2017/8/18.
 */
public class WechatRewardListModel {

    private WechatReward wechatReward;

    private MachineModel machineModel;

    public WechatReward getWechatReward() {
        return wechatReward;
    }

    public void setWechatReward(WechatReward wechatReward) {
        this.wechatReward = wechatReward;
    }

    public MachineModel getMachineModel() {
        return machineModel;
    }

    public void setMachineModel(MachineModel machineModel) {
        this.machineModel = machineModel;
    }
}
