package com.onyouxi.service;

import com.onyouxi.model.dbModel.*;
import com.onyouxi.model.pageModel.MsgTmpl;
import com.onyouxi.utils.WeixinUtil;
import com.onyouxi.wechat.pojo.AccessToken;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@EnableScheduling
public class ZhuawawaService {

    @Autowired
    private WechatUserPlayService wechatUserPlayService;

    @Autowired
    private WechatMachineService wechatMachineService;

    @Autowired
    private MachineService machineService;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private PrizeService prizeService;

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
            if( null ==wechatUserModel.getMoney() || wechatUserModel.getMoney() <=0){
                return "你已经没有游戏币了，请充值";
            }
            if (machineModel.getCanUse() == 1) {
                return "机器停止工作";
            }
            if(machineModel.getStatus() == 1){
                return "有人正在游戏中，请稍后！";
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
            if(machineModel.getStatus() == 3 ){
                if(!machineModel.getCurrentWechatId().equals(wechatId)){
                    return "有人正在游戏中，请稍后";
                }
            }
            updatePlayInfo(wechatId,machineId);
            return null;
        }
    }

    private void updatePlayInfo(String wechatId,String machineId){
        //TODO需要事务 轮到此人玩，删除队列信息，更新机器状态
        wechatMachineService.del(wechatId);
        MachineModel machineModel = machineService.updateStatus(machineId,1,wechatId);
        //保存一条游戏记录
        WechatUserPlayModel wechatUserPlayModel = new WechatUserPlayModel();
        wechatUserPlayModel.setMachineId(machineId);
        wechatUserPlayModel.setWechatUserId(wechatId);
        wechatUserPlayModel.setStartTime(new Date());
        wechatUserPlayModel.setStatus(0);
        wechatUserPlayService.save(wechatUserPlayModel);
        //减少一次游戏次数
        PrizeModel prizeModel = prizeService.findById(machineModel.getPrizeId());
        wechatUserService.updateWechatUserPlayNum(wechatId,-prizeModel.getMoney());
    }

    /**
     * 预约
     * @param wechatId
     * @param machineId
     * @return
     */
    public String  reserve(String wechatId,String machineId){
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
            if( wechatUserModel.getMoney() <=0){
                return "你已经没有游戏币了，请充值";
            }
            if (machineModel.getCanUse() == 1) {
                return "机器停止工作";
            }
            if(machineModel.getStatus() == 0){
                updatePlayInfo(wechatId,machineId);
                return "play";
            }
            if( null != wechatMachineModel){
                return "您已经在预约中了，请耐心等待！";
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
    public String gameOver(String machineCode,Integer status){
        if(StringUtils.isEmpty(machineCode)){
            return "机器code不存在";
        }
        MachineModel machineModel = machineService.findByCode(machineCode);
        if( null == machineModel){
            return "机器不存在";
        }
        List<WechatUserPlayModel> wechatUserPlayModelList = wechatUserPlayService.findByWechatUserIdAndMachineIdAndStatus(machineModel.getCurrentWechatId(),machineModel.getId(),0);
        WechatUserPlayModel wechatUserPlayModel = null;
        if( null != wechatUserPlayModelList && wechatUserPlayModelList.size() > 0){
            wechatUserPlayModel = wechatUserPlayModelList.get(0);
        }
        if( null == wechatUserPlayModel){
            return "错误的参数";
        }
        if( wechatUserPlayModel.getStatus() > 1){
            return "本局游戏已经结束";
        }
        WechatUserModel wechatUserModel = wechatUserService.findById(wechatUserPlayModel.getWechatUserId());
        if( null ==wechatUserModel){
            return "该用户不存在";
        }
        //更新游戏记录
        if(status==0){
            wechatUserPlayService.updateStatus(wechatUserPlayModel.getId(),10,null);
        }else{
            wechatUserPlayService.updateStatus(wechatUserPlayModel.getId(),20,machineModel.getPrizeId());
        }

        //该用户没有游戏次数了
        if(wechatUserModel.getMoney()<=0){
            List<WechatMachineModel> wechatMachineModelList = wechatMachineService.findByMachineId(wechatUserPlayModel.getMachineId());
            //当没有人在排队了
           // if( null == wechatMachineModelList || wechatMachineModelList.size() == 0){
                machineService.updateStatus(wechatUserPlayModel.getMachineId(),0,null);
           // }else{
                //当还有人在排队的时候 TODO需要通知队列里的第一个人
            //    machineService.updateStatus(wechatUserPlayModel.getMachineId(),2,null);
            //    queueNotice(wechatUserPlayModel.getMachineId());
            //}
            return "no";
        }else{
            //当用户还有游戏次数的时候，提醒用户是否继续
            //将机器设置为等待用户决定的状态
            machineService.updateStatus(machineModel.getId(),3,wechatUserModel.getId());
            return "play";
        }
    }

    /**
     *
     *重新开始游戏
     * @return
     */
    public String restartGame(String machineCode,String wechatId){
        if(StringUtils.isEmpty(machineCode)){
            return "机器code不存在";
        }
        MachineModel machineModel = machineService.findByCode(machineCode);
        if( null == machineModel){
            return "机器不存在";
        }

        WechatUserModel wechatUserModel = wechatUserService.findById(wechatId);
        if( null ==wechatUserModel){
            return "该用户不存在";
        }
        //该用户没有游戏次数了
        if(wechatUserModel.getMoney()<=0){
            machineService.updateStatus(machineModel.getId(),0,null);
            return "no";
        }else{
            //当用户还有游戏次数的时候，重新开始游戏
            //将机器设置为重新开始
            updatePlayInfo(machineModel.getCurrentWechatId(),machineModel.getId());
            machineService.updateStatus(machineModel.getId(),1,wechatUserModel.getId());
            return "play";
        }
    }

    /**
     * 游戏结束
     *
     * @return
     */
    public String endGame(String machineId,String wechatId){
        if(StringUtils.isEmpty(machineId)){
            return "机器编号s不存在";
        }
        MachineModel machineModel = machineService.findById(machineId);
        if( null == machineModel){
            return "错误的参数";
        }
        if( !machineModel.getCurrentWechatId().equals(wechatId)){
            return "您不能结束不是您玩的娃娃机";
        }

        machineService.updateStatus(machineId,0,null);
        return null;
    }

    /**
     * 游戏超时
     * @return
     */
    public String overTime(String wechatPlayId){
        if(StringUtils.isEmpty(wechatPlayId) ){
            return "错误的参数";
        }
        WechatUserPlayModel wechatUserPlayModel = wechatUserPlayService.findById(wechatPlayId);
        if( null == wechatUserPlayModel){
            return "错误的参数";
        }
        if( wechatUserPlayModel.getStatus() > 1){
            return "本局游戏已经结束";
        }
        if(null == wechatUserPlayModel.getEndTime() ){
            long gameTime = new Date().getTime() - wechatUserPlayModel.getStartTime().getTime();
            if(gameTime > 30*1000){
                wechatUserPlayService.updateStatus(wechatPlayId,1,null);
                //List<WechatMachineModel> wechatMachineModelList = wechatMachineService.findByMachineId(wechatUserPlayModel.getMachineId());
                //当没有人在排队了
                //if( null == wechatMachineModelList || wechatMachineModelList.size() == 0){
                    //machineService.updateStatus(wechatUserPlayModel.getMachineId(),0,null);
                //}else{
                    //当还有人在排队的时候 TODO需要通知队列里的第一个人
                    //machineService.updateStatus(wechatUserPlayModel.getMachineId(),2,null);
                    //queueNotice(wechatUserPlayModel.getMachineId());
                //}
                return "overTime";
            }
        }
        if( wechatUserPlayModel.getStatus() == 1){
            long waitTime = new Date().getTime() - wechatUserPlayModel.getEndTime().getTime();
            if(waitTime > 30*1000){
                wechatUserPlayService.updateStatus(wechatPlayId,11,null);
                machineService.updateStatus(wechatUserPlayModel.getMachineId(),0,null);
            }
        }
        return null;
    }

    public void queueNotice(String machineId){
        MachineModel machineModel = machineService.findById(machineId);
        if( null != machineModel){
            queueLogic(machineModel);
        }
    }

    private void queueLogic(MachineModel machineModel){
        if(machineModel.getCanUse() == 0 && machineModel.getStatus() ==2){
            List<WechatMachineModel> wechatMachineModelList = wechatMachineService.findByMachineId(machineModel.getId());
            if( null != wechatMachineModelList && wechatMachineModelList.size() > 0){
                //取到排队的第一个用户
                WechatMachineModel wechatMachineModel = wechatMachineModelList.get(0);
                WechatUserModel wechatUserModel = wechatUserService.findById(wechatMachineModel.getWechatId());
                MsgTmpl notifyMsg = new MsgTmpl();
                notifyMsg.setTouser(wechatUserModel.getOpenId());

                if( null == wechatMachineModel.getNotifyTime()){
                    sendMsg(notifyMsg);
                    wechatMachineService.updateNotifyTime(wechatMachineModel.getId());
                    log.info("发送微信通知:"+wechatUserModel.getNick());
                }else{
                    long a = new Date().getTime() - wechatMachineModel.getNotifyTime().getTime();
                    //大于3分钟并且小于6分钟通知第二次
                    if( a > 3*60*1000 && a < 6*60*1000){
                        sendMsg(notifyMsg);
                        wechatMachineService.updateNotifyTime(wechatMachineModel.getId());
                        log.info("发送微信通知:"+wechatUserModel.getNick());
                    }else if( a > 6*60*1000){
                        //大于6分钟，相当于放弃 删除排队数据
                        wechatMachineService.del(wechatMachineModel.getId());
                        //当排队的人没有的时候,修改娃娃机的状态
                        if(wechatMachineModelList.size() == 1){
                            machineService.updateStatus(machineModel.getId(),0,null);
                        }
                        //发送放弃通知
                        MsgTmpl failMsg = new MsgTmpl();
                        sendMsg(failMsg);
                        log.info("发送微信放弃通知:"+wechatUserModel.getNick());
                    }
                }


            }
        }
    }
    /**
     * 排队通知 每分钟通知一次
    @Scheduled(cron="0/10 * * * * ?")
    public void queueNotice(){
        List<MachineModel> machineModelList = machineService.findAll();
        if( null != machineModelList){
            for(MachineModel machineModel : machineModelList){
                queueLogic(machineModel);
            }
        }
    }
     */

    /**
     * 每2秒检查一次游戏时间
     */
    @Scheduled(cron="0/2 * * * * ?")
    public void checkPlayTime(){
        List<WechatUserPlayModel> wechatUserPlayModelList = wechatUserPlayService.findAll();
        if( null != wechatUserPlayModelList && wechatUserPlayModelList.size() > 0){
            for(WechatUserPlayModel wechatUserPlayModel : wechatUserPlayModelList){
                if(wechatUserPlayModel.getStatus() < 10 ){
                    Date now = new Date();
                    long a = now.getTime() - wechatUserPlayModel.getStartTime().getTime();
                    //如果大于30秒
                    if( a > 30*1000){
                        log.info("游戏超时:"+wechatUserPlayModel.getWechatUserId());
                        this.overTime(wechatUserPlayModel.getId());
                    }
                }
            }
        }

    }

    /**
     * 发送微信通知
     * @param msgTmpl
     */
    public void sendMsg(MsgTmpl msgTmpl){
        //AccessToken accessToken = wechatUserService.getAccessToken();
        //WeixinUtil.sendTmpl(JSONObject.fromObject(msgTmpl).toString(), accessToken.getToken());
    }


}
