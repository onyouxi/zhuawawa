package com.onyouxi.util;

import com.alibaba.fastjson.JSON;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by administrator on 2017/8/9.
 */
@Slf4j
@Service
public class ArduinoSerialUtil {



    @Autowired
    private ConfigUtil configUtil;

    private InputStream in;

    private OutputStream out;

    private static Integer status = 0;

    private static ChannelHandlerContext ctx;

    public void initChannel(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }


    public static class SerialReader implements Runnable
    {
        InputStream in;

        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        public void run () {
            byte[] buffer = new byte[1024];
            int len = -1;

            try {
                String cmd = null;
                while ( ( len = this.in.read(buffer)) > -1 ) {
                    cmd = new String(buffer,0,len);
                    if(!StringUtils.isEmpty(cmd)){
                        if("s".equals(cmd)){
                            //当抓娃娃指令执行成功后
                            status = 0;
                        }else if("e".equals(cmd)){
                            //当抓娃娃机结束,并没有抓到娃娃的时候
                            Map<String,Object> map = new HashMap<>();
                            map.put("type","end");
                            map.put("status",1);
                            ctx.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(map)));
                        }else if("".equals(cmd)){
                            //当抓娃娃机结束，并且抓到娃娃的时候
                            Map<String,Object> map = new HashMap<>();
                            map.put("type","end");
                            map.put("status",0);
                            ctx.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(map)));
                        }
                    }
                    System.out.print(cmd);
                }

            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    public void write(String msg) throws Exception {
        if( null == out){
            this.connect();
        }
        try {
            status = 1;
            System.out.println("write msg:"+msg);
            // 进行输入输出操作
            OutputStreamWriter writer = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(writer);
            for (int i = 0; i < 3; i++) {
                if(status == 1){
                    bw.write(msg);
                    bw.flush();
                    System.out.println("write msg:"+msg+" num:"+i+" status:"+status);
                    Thread.sleep(500);
                }
            }
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws Exception {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(configUtil.getArduinoSerialUrl());
        if ( portIdentifier.isCurrentlyOwned() ) {
            System.out.println("Error: Port is currently in use");
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            if ( commPort instanceof SerialPort ) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();
                (new Thread(new SerialReader(in))).start();
            } else{
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }

    /**
    public static void main(String[] args) {
        try {
            ArduinoSerialUtil arduinoSerialUtil = new ArduinoSerialUtil();
            arduinoSerialUtil.connect("/dev/cu.usbmodem1411");
            arduinoSerialUtil.write("a");
            arduinoSerialUtil.write("b");
            arduinoSerialUtil.write("fkfk");
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }**/
}
