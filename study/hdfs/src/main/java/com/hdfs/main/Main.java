package com.hdfs.main;

import com.hdfs.utils.HDFSRead;
import com.hdfs.utils.HDFSWrite;

public class Main {
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("未输入参数！");
            help();
            return;
        }

        switch(args[0]){
            case "-r":
            case "-R":
                HDFSRead.read(args[1]);
                break;
            case "-w":
            case "-W":
                HDFSWrite.write(args[1],args[2]);
                break;
            default:
                help();
                break;
        }
    }
    public static void help(){

        System.out.println("-r -R read");
        System.out.println("-w -W read");

    }
}
