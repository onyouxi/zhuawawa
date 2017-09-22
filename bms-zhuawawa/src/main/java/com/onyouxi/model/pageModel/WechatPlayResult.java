package com.onyouxi.model.pageModel;

import com.onyouxi.model.dbModel.PrizeModel;
import com.onyouxi.model.dbModel.WechatUserPlayModel;

/**
 * Created by administrator on 2017/9/21.
 */
public class WechatPlayResult {

    private WechatUserPlayModel wechatUserPlayModel;

    private PrizeModel prizeModel;

    public WechatUserPlayModel getWechatUserPlayModel() {
        return wechatUserPlayModel;
    }

    public void setWechatUserPlayModel(WechatUserPlayModel wechatUserPlayModel) {
        this.wechatUserPlayModel = wechatUserPlayModel;
    }

    public PrizeModel getPrizeModel() {
        return prizeModel;
    }

    public void setPrizeModel(PrizeModel prizeModel) {
        this.prizeModel = prizeModel;
    }
}
