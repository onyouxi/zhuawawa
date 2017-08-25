package com.onyouxi.model.pageModel;

/**
 * Created by administrator on 2017/8/25.
 */
public class UserH5SocketModel {

    //状态码
    private Integer result;

    //指令
    private String cmd;

    //消息
    private String msg;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
