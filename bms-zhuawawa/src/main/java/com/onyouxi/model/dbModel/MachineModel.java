package com.onyouxi.model.dbModel;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * Created by administrator on 2017/8/18.
 */

@Document(collection = "machine")
public class MachineModel {

    @Field("_id")
    private String id;

    private String name;

    private String code;

    //描述信息
    private String des;

    //机器状态 0空闲  1游戏中 2排队中
    private Integer status;

    //0运行中  1停止使用
    private Integer canUse;

    private Date createTime;

    //正在玩的用户
    private String currentWechatId;

    //当前的奖品
    private String prizeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCanUse() {
        return canUse;
    }

    public void setCanUse(Integer canUse) {
        this.canUse = canUse;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCurrentWechatId() {
        return currentWechatId;
    }

    public void setCurrentWechatId(String currentWechatId) {
        this.currentWechatId = currentWechatId;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }
}
