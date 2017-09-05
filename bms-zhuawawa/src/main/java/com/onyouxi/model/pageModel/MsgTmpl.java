package com.onyouxi.model.pageModel;

import lombok.Data;

import java.util.Map;

/**
 * Created by administrator on 2017/9/5.
 */
@Data
public class MsgTmpl {
    private String touser;

    private String template_id;

    private String url;

    private String topcolor;

    private Map<String,ValueTmpl> data;
}
