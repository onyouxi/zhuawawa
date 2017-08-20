package com.onyouxi.util;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.io.*;

/**
 * Created by administrator on 2017/8/9.
 */
@Slf4j
@Service
public class ArduinoSerialUtil {

    @Value("${arduinoSerialUrl}")
    private String arduinoSerialUrl;

    private InputStream in;

    private OutputStream out;

    private static Integer status = 0;

    public static class SerialReader implements Runnable
    {
        InputStream in;

        public SerialReader ( InputStream in )
        {
            this.in = in;
        }


        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
                String cmd = null;
                while ( ( len = this.in.read(buffer)) > -1 ) {
                    cmd = new String(buffer,0,len);
                    if(!StringUtils.isEmpty(cmd) && "s".equals(cmd)){
                        status = 0;
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
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(arduinoSerialUrl);
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
