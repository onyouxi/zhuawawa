package com.onyouxi.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onyouxi.util.ArduinoSerialUtil;
import com.onyouxi.util.CmdConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by administrator on 2017/8/9.
 */
@Slf4j
@Service
public class ZhuawawaService {

    private ArduinoSerialUtil arduinoSerialUtil;

    @PostConstruct
    public void init() throws Exception {
        arduinoSerialUtil = new ArduinoSerialUtil();
        arduinoSerialUtil.connect("/dev/cu.usbmodem1411");
    }

    /**
     * 给娃娃机加币
     * @throws InterruptedException
     */
    public void add() throws InterruptedException {
        arduinoSerialUtil.write(CmdConst.ADD);
    }

    /**
     * 结束当前指令
     * @throws InterruptedException
     */
    public void end() throws InterruptedException {
        arduinoSerialUtil.write(CmdConst.END);
    }

    /**
     * 向左移动
     * @throws InterruptedException
     */
    public void left() throws InterruptedException {
        arduinoSerialUtil.write(CmdConst.LEFT);
    }

    /**
     * 向右移动
     * @throws InterruptedException
     */
    public void right() throws InterruptedException {
        arduinoSerialUtil.write(CmdConst.RIGHT);
    }

    /**
     * 向前移动
     * @throws InterruptedException
     */
    public void up() throws InterruptedException {
        arduinoSerialUtil.write(CmdConst.UP);
    }

    /**
     * 向后移动
     * @throws InterruptedException
     */
    public void down() throws InterruptedException {
        arduinoSerialUtil.write(CmdConst.DOWN);
    }

    /**
     * 抓娃娃
     * @throws InterruptedException
     */
    public void zhua() throws InterruptedException {
        arduinoSerialUtil.write(CmdConst.ZHUA);
    }


    public void action(String jsonCmd) throws InterruptedException {
        JSONObject jsonObject = JSON.parseObject(jsonCmd);
        String cmd = jsonObject.getString("cmd");
        if( "end".equals(cmd)){
            this.end();
        }else if( "add".equals(cmd)) {
            this.add();
        }else if( "left".equals(cmd)){
            this.left();
        }else if( "right".equals(cmd)){
            this.right();
        }else if( "up".equals(cmd)){
            this.up();
        }else if( "down".equals(cmd)){
            this.down();
        }else if( "zhua".equals(cmd)){
            this.zhua();
        }
    }


}
