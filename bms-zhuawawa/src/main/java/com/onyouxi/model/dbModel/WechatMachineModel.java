package com.onyouxi.model.dbModel;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by administrator on 2017/8/21.
 */
@Document(collection = "wechat_machine")
public class WechatMachineModel {

    private String id;

    private String wechatId;

    private String machineId;

    private Date createTime=new Date();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
