package com.onyouxi.model.pageModel;

import com.onyouxi.wechat.pojo.AccessToken;

import java.util.Date;

/**
 * Created by administrator on 2017/8/20.
 */
public class AccessTokenCacheModel {

    private AccessToken accessToken;

    private Date createTime;

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
