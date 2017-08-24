package com.onyouxi.model.pageModel;

import com.onyouxi.model.dbModel.MachineModel;
import com.onyouxi.model.dbModel.PrizeModel;
import com.onyouxi.model.dbModel.WechatUserModel;

/**
 * Created by administrator on 2017/8/23.
 */
public class MachineResult {

    private MachineModel machineModel;

    private WechatUserModel wechatUserModel;

    private PrizeModel prizeModel;

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

    public PrizeModel getPrizeModel() {
        return prizeModel;
    }

    public void setPrizeModel(PrizeModel prizeModel) {
        this.prizeModel = prizeModel;
    }
}
