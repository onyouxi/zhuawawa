package com.onyouxi.service;

import com.onyouxi.model.dbModel.MachineModel;
import com.onyouxi.model.dbModel.WechatMachineModel;
import com.onyouxi.model.dbModel.WechatUserModel;
import com.onyouxi.model.dbModel.WechatUserPlayModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by administrator on 2017/8/22.
 */
@Service
@Slf4j
public class ZhuawawaService {

    @Autowired
    private WechatUserPlayService wechatUserPlayService;

    @Autowired
    private WechatMachineService wechatMachineService;

    @Autowired
    private MachineService machineService;

    @Autowired
    private WechatUserService wechatUserService;

    private byte[] startlock = new byte[0];

    private byte[] queuelock = new byte[0];

    /**
     * 开始游戏
     * @param wechatId
     * @param machineId
     * @return
     */
    public String start(String wechatId,String machineId){
        synchronized(startlock) {
            MachineModel machineModel = machineService.findById(machineId);
            WechatUserModel wechatUserModel = wechatUserService.findById(wechatId);
            if (null == machineModel) {
                return "机器不存在";
            }
            if( null ==wechatUserModel){
                return "该用户不存在";
            }
            if( null ==wechatUserModel.getPlayNum() || wechatUserModel.getPlayNum() <=0){
                return "你已经没有游戏次数了，请充值";
            }
            if (machineModel.getCanUse() == 1) {
                return "机器停止工作";
            }
            if(machineModel.getStatus() == 1){
                return "有人正在游戏中，请排队！";
            }
            if(machineModel.getStatus() == 2){
                WechatMachineModel wechatMachineModel = wechatMachineService.findByWechatId(wechatId);
                if( null == wechatMachineModel){
                    wechatMachineModel = new WechatMachineModel();
                    wechatMachineModel.setMachineId(machineId);
                    wechatMachineModel.setWechatId(wechatId);
                    wechatMachineService.save(wechatMachineModel);
                    return "已经被人预定，已经将您放入到队列中！";
                }
            }
            updatePlayInfo(wechatId,machineId);
            return null;
        }
    }

    private void updatePlayInfo(String wechatId,String machineId){
        //TODO需要事务 轮到此人玩，删除队列信息，更新机器状态
        wechatMachineService.del(wechatId);
        machineService.updateStatus(machineId,1,wechatId);
        //保存一条游戏记录
        WechatUserPlayModel wechatUserPlayModel = new WechatUserPlayModel();
        wechatUserPlayModel.setMachineId(machineId);
        wechatUserPlayModel.setWechatUserId(wechatId);
        wechatUserPlayModel.setStartTime(new Date());
        wechatUserPlayModel.setStatus(0);
        wechatUserPlayService.save(wechatUserPlayModel);
        //减少一次游戏次数
        wechatUserService.updateWechatUserPlayNum(wechatId,-1);
    }

    /**
     * 排队
     * @param wechatId
     * @param machineId
     * @return
     */
    public String  queue(String wechatId,String machineId){
        synchronized(queuelock) {
            MachineModel machineModel = machineService.findById(machineId);
            WechatUserModel wechatUserModel = wechatUserService.findById(wechatId);
            WechatMachineModel wechatMachineModel = wechatMachineService.findByWechatId(wechatId);
            if (null == machineModel) {
                return "机器不存在";
            }
            if( null ==wechatUserModel){
                return "该用户不存在";
            }
            if( wechatUserModel.getPlayNum() <=0){
                return "你已经没有游戏次数了，请充值";
            }
            if (machineModel.getCanUse() == 1) {
                return "机器停止工作";
            }
            if(machineModel.getStatus() == 0){
                updatePlayInfo(wechatId,machineId);
                return "play";
            }
            if( null != wechatMachineModel){
                return "您已经在排队了，请耐心等待！";
            }
            wechatMachineModel = new WechatMachineModel();
            wechatMachineModel.setMachineId(machineId);
            wechatMachineModel.setWechatId(wechatId);
            wechatMachineService.save(wechatMachineModel);
            return null;
        }
    }

    /**
     * 游戏结束
     *
     * @return
     */
    public String gameOver(String wechatPlayId , String prizeId){
        if(StringUtils.isEmpty(wechatPlayId) ){
            return "错误的参数";
        }
        WechatUserPlayModel wechatUserPlayModel = wechatUserPlayService.findById(wechatPlayId);
        if( null == wechatUserPlayModel){
            return "错误的参数";
        }
        if( wechatUserPlayModel.getStatus() > 0){
            return "本局游戏已经结束";
        }
        MachineModel machineModel = machineService.findById(wechatUserPlayModel.getMachineId());
        WechatUserModel wechatUserModel = wechatUserService.findById(wechatUserPlayModel.getWechatUserId());
        if (null == machineModel) {
            return "机器不存在";
        }
        if( null ==wechatUserModel){
            return "该用户不存在";
        }

        //更新游戏记录
        if(StringUtils.isEmpty(prizeId)){
            wechatUserPlayService.updateStatus(wechatPlayId,1,null);
        }else{
            wechatUserPlayService.updateStatus(wechatPlayId,2,prizeId);
        }

        //该用户没有游戏次数了
        if(wechatUserModel.getPlayNum()<=0){
            List<WechatMachineModel> wechatMachineModelList = wechatMachineService.findByMachineId(wechatUserPlayModel.getMachineId());
            //当没有人在排队了
            if( null == wechatMachineModelList || wechatMachineModelList.size() == 0){
                machineService.updateStatus(wechatUserPlayModel.getMachineId(),0,null);
            }else{
                //当还有人在排队的时候 TODO需要通知队列里的第一个人
                machineService.updateStatus(wechatUserPlayModel.getMachineId(),2,null);
                WechatMachineModel wechatMachineModel = wechatMachineModelList.get(0);
            }
        }else{
            //当用户还有游戏次数的时候，提醒用户是否继续
            return "play";
        }

        return null;
    }

    /**
     * 游戏超时
     * @return
     */
    public String overTime(String wechatPlayId,String machineId){
        if(StringUtils.isEmpty(wechatPlayId) ){
            return "错误的参数";
        }
        WechatUserPlayModel wechatUserPlayModel = wechatUserPlayService.findById(wechatPlayId);
        if( null == wechatUserPlayModel){
            return "错误的参数";
        }
        if( wechatUserPlayModel.getStatus() > 0){
            return "本局游戏已经结束";
        }
        if(null == wechatUserPlayModel.getEndTime() ){
            long gameTime = new Date().getTime() - wechatUserPlayModel.getStartTime().getTime();
            if(gameTime > 30*1000){
                wechatUserPlayService.updateStatus(wechatPlayId,1,null);
                List<WechatMachineModel> wechatMachineModelList = wechatMachineService.findByMachineId(wechatUserPlayModel.getMachineId());
                //当没有人在排队了
                if( null == wechatMachineModelList || wechatMachineModelList.size() == 0){
                    machineService.updateStatus(wechatUserPlayModel.getMachineId(),0,null);
                }else{
                    //当还有人在排队的时候 TODO需要通知队列里的第一个人
                    machineService.updateStatus(wechatUserPlayModel.getMachineId(),2,null);
                    WechatMachineModel wechatMachineModel = wechatMachineModelList.get(0);
                }
                return "overTime";
            }
        }
        return null;
    }

    /**
     * 排队通知 每分钟通知一次
     */
    @Scheduled(cron="0 30 * * * ?")
    public void queueNotice(){


    }


}
