package com.onyouxi.model.dbModel;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Created by administrator on 2017/8/22.
 */
@Document(collection = "wechat_user_play")
public class WechatUserPlayModel {

    @Field("_id")
    private String id;

    private String wechatUserId;

    private String machineId;

    //游戏开始时间
    private Date startTime;

    //0游戏进行中  1游戏结束但没有抓到娃娃 2游戏结束但抓到娃娃
    private Integer status;

    //游戏结束时间
    private Date endTime;

    //抓到的奖品的Id
    private String prizeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }
}
