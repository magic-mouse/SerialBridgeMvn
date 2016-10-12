package com.github.magicmouse.serialBridge;


public class SerialBridge {

    public static void main(String[] args) {
        try {
            String com1 = "";
            String com2 = "";
            String baud = "";

            for(String arg : args ){

                String[] part= arg.split(":");
                switch(part[0]){
                    case "-com1":
                        com1 = part[1];
                        break;
                    case "-com2":
                        com2 = part[1];
                        break;
                    case "-baud":
                        baud = part[1];
                        break;
                    default:
                        throw new Exception("Invalid argument exception: either you added an extra argument or you didnt have what you should.");
                }

            }

            if(com1.isEmpty() || com2.isEmpty() ){
                throw new Exception("Missing argument exception: please use both -com1: and -com2: as arguments");
            }
            System.out.println("Program is running untill terminated, use \"CTRL+C\" to terminate");
            RunClass rc = new RunClass(com1, com2, baud);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




}
