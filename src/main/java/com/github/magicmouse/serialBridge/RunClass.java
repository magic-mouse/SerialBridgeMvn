package com.github.magicmouse.serialBridge;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class RunClass {
    static SerialPort sp1;
    static SerialPort sp2;

    public RunClass(String com1, String com2, String strBaudIn) {
        String firstCom = com1;
        String secondCom = com2;
        String strBaud = strBaudIn;
        int baud;
        if(strBaud == ""){
            baud = SerialPort.BAUDRATE_9600;
        }else{
            baud = Integer.parseInt(strBaud);
        }

        int databits = SerialPort.DATABITS_8;
        int stopbit = SerialPort.STOPBITS_1;
        int parity = SerialPort.PARITY_NONE;

        sp1 = new SerialPort(firstCom);
        sp2 = new SerialPort(secondCom);

        try {
            sp1.openPort();
            sp2.openPort();
            System.out.println("Port opened");
            sp1.setParams(baud, databits, stopbit, parity);
            System.out.println("Params set: " + baud + ", " + databits + ", " + stopbit + ", " + parity);
            sp2.setParams(baud, databits, stopbit, parity);

            int mask = SerialPort.MASK_RXCHAR;
            sp1.setEventsMask(mask);
            sp2.setEventsMask(mask);
            sp1.addEventListener(new SerialPort1Reader());
            sp2.addEventListener(new SerialPort2Reader());

            while(true);

        } catch (SerialPortException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static class SerialPort1Reader implements SerialPortEventListener {
        @Override
        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR()){
                if(event.getEventValue() == 1){
                    try {
                        sp2.writeBytes(sp1.readBytes(1));
                    }
                    catch (SerialPortException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
    }

    static class SerialPort2Reader implements SerialPortEventListener {
        @Override
        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR()){
                if(event.getEventValue() == 1){
                    try {
                        sp1.writeBytes(sp2.readBytes(1));
                    }
                    catch (SerialPortException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }


    }

}
