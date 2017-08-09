package cn.partytime.service;

import cn.partytime.util.ArduinoSerialUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by administrator on 2017/8/9.
 */
@Service
public class ZhuawawaService {

    private ArduinoSerialUtil arduinoSerialUtil;

    @PostConstruct
    public void init() throws Exception {
        arduinoSerialUtil = new ArduinoSerialUtil();
        arduinoSerialUtil.connect("/dev/cu.usbmodem1411");
    }


}
