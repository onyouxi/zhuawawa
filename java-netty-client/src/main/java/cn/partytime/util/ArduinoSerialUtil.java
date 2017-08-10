package cn.partytime.util;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.*;

/**
 * Created by administrator on 2017/8/9.
 */
public class ArduinoSerialUtil {

    private InputStream in;

    private OutputStream out;

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
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                    String cmd = new String(buffer,0,len);
                    System.out.print(cmd);
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    public void write(String msg) throws InterruptedException {
        try {
            Thread.sleep(2000);// 链接后暂停2秒再继续执行
            System.out.println("connected!");
            // 进行输入输出操作
            OutputStreamWriter writer = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(writer);
            for (int i = 0; i < 3; i++) {
                bw.write(msg);
                bw.flush();
            }
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
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
    }
}
