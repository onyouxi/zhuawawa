package com.onyouxi.wechat.entity;

/**
 * Created by administrator on 2017/9/28.
 */
public class ReceiveUnifiedOrderXmlEntity {

    //返回状态码
    private String return_code;

    //返回信息
    private String return_msg;

    //公众账号ID
    private String appid;

    //商户号
    private String mch_id;

    //设备号
    private String device_info;

    //随机字符串
    private String nonce_str;

    //签名
    private String sign;

    //业务结果
    private String result_code;

    //错误代码
    private String err_code;

    //错误代码描述
    private String err_code_des;

    //交易类型 JSAPI，NATIVE，APPs
    private String trade_type;

    //预支付交易会话标识 该值有效期为2小时
    private String prepay_id;

    //二维码链接 trade_type为NATIVE是有返回
    private String code_url;
}
