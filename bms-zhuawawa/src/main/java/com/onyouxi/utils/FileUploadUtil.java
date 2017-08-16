package com.onyouxi.utils;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * Created by liuwei on 2016/8/19.
 */

@ConfigurationProperties(prefix = "fileUpload")
public class FileUploadUtil {

    //当上传文件超过限制时设定的临时文件位置，注意是绝对路径
    private String tempPath;

    //文件上传目标目录，注意是绝对路径
    private String dstPath;

    //设置允许用户上传文件大小,单位:字节)
    private String maxSize;

    //H5背景图保存路径
    private String h5BackgroundPath;

    //表情包大图保存路径
    private String bigExpressionsPath;

    //表情包小图保存路径
    private String smallExpressionsPath;

    //特效图片保存路径
    private String specialImagesPath;

    //特效视频保存路径
    private String specialVideosPath;

    //文件访问地址
    private String url;

    private String h5FileName = "h5background";

    /**
     * 返回活动的基础路径
     * @param partyShortName
     * @return
     */
    private String getBasePartyPath(String partyShortName){
        return this.dstPath+ File.separator+partyShortName+File.separator;
    }

    public String getSaveH5Path(String partyShortName,String name){
        String ext = this.getFileExt(name);
        File file = new File(getBasePartyPath(partyShortName)+this.h5BackgroundPath);
        if(!file.exists()){
            file.mkdirs();
        }
        return getBasePartyPath(partyShortName)+this.h5BackgroundPath+ File.separator+this.h5FileName+ext;
    }

    public String getSaveBigExpressionsPath(String partyShortName){
        String path = getBasePartyPath(partyShortName)+this.bigExpressionsPath;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path;
    }

    public String getSaveSmallExpressionsPath(String partyShortName){
        String path = getBasePartyPath(partyShortName)+this.smallExpressionsPath;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path;
    }

    public String getSaveSpecialImagesPath(String partyShortName){
        String path= getBasePartyPath(partyShortName)+this.specialImagesPath;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path;
    }

    public String getSaveSpecialVideosPath(String partyShortName){
        String path= getBasePartyPath(partyShortName)+this.specialVideosPath;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path;
    }

    public String getFileExt(String name){
        if(!StringUtils.isEmpty(name)){
            if( name.indexOf(".") == -1){
                return null;
            }else{
               return name.substring(name.indexOf("."));
            }
        }else{
            return null;
        }
    }

    public String getH5BackgroundUrl(String partyShortName,String name){
        String ext = this.getFileExt(name);
        return this.url+"/"+partyShortName+"/"+this.h5FileName+"/"+this.h5FileName+ext;
    }

    public String getBigExpressionsUrl(String partyShortName){
        return this.url+"/"+partyShortName+"/"+this.bigExpressionsPath+"/";
    }

    public String getSmallExpressionsUrl(String partyShortName){
        return this.url+"/"+partyShortName+"/"+this.smallExpressionsPath+"/";
    }

    public String getSpecialImagesUrl(String partyShortName){
        return this.url+"/"+partyShortName+"/"+this.specialImagesPath+"/";
    }

    public String getSpecialVideoUrl(String partyShortName){
        return this.url+"/"+partyShortName+"/"+this.specialVideosPath+"/";
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public String getDstPath() {
        return dstPath;
    }

    public void setDstPath(String dstPath) {
        this.dstPath = dstPath;
    }

    public String getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public String getH5BackgroundPath() {
        return h5BackgroundPath;
    }

    public void setH5BackgroundPath(String h5BackgroundPath) {
        this.h5BackgroundPath = h5BackgroundPath;
    }

    public String getBigExpressionsPath() {
        return bigExpressionsPath;
    }

    public void setBigExpressionsPath(String bigExpressionsPath) {
        this.bigExpressionsPath = bigExpressionsPath;
    }

    public String getSmallExpressionsPath() {
        return smallExpressionsPath;
    }

    public void setSmallExpressionsPath(String smallExpressionsPath) {
        this.smallExpressionsPath = smallExpressionsPath;
    }

    public String getSpecialImagesPath() {
        return specialImagesPath;
    }

    public void setSpecialImagesPath(String specialImagesPath) {
        this.specialImagesPath = specialImagesPath;
    }

    public String getSpecialVideosPath() {
        return specialVideosPath;
    }

    public void setSpecialVideosPath(String specialVideosPath) {
        this.specialVideosPath = specialVideosPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
