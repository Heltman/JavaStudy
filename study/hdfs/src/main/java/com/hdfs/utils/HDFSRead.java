package com.hdfs.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

public class HDFSRead {
    public static void read(String path) {
        Configuration conf = new Configuration();
        FSDataInputStream in = null;
        FileSystem fs = null;
        try {
            fs = FileSystem.get(conf);
            Path path1 = new Path(path);
            if (! fs.exists(path1) ) {
                System.out.println("路径不存在！");
                return;
            } else if (fs.isDirectory(path1)) {
                System.out.println("路径是一个文件夹！");
                return;
            }
            in = fs.open(path1);
            byte[] b = new byte[1024];
            int len;
            while ((len = in.read(b)) != -1) {
                System.out.println(new String(b, 0, len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
