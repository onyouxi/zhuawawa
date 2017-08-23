package com.onyouxi.model.dbModel;

import java.util.Date;

/**
 * Created by administrator on 2017/8/23.
 * 通知记录
 */
public class QueueNoticeModel {

    private String id;

    private String machineId;

    private String lastWechatId;

    private Date noticeTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getLastWechatId() {
        return lastWechatId;
    }

    public void setLastWechatId(String lastWechatId) {
        this.lastWechatId = lastWechatId;
    }

    public Date getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }
}
