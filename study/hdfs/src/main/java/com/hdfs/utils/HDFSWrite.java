package com.hdfs.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class HDFSWrite {
    public static void write(String filename, String uri) {
        Configuration conf = new Configuration();
        FileSystem fs = null;
        Path path = new Path(uri);
        InputStream inputStream = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("本地文件不存在！");
                return;
            } else if (file.isDirectory()) {
                System.out.println("本地是一个目录");
                return;
            }
            try {
                fs = FileSystem.get(URI.create("hdfs://192.168.134.128"), conf, "hadoop");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (! fs.isDirectory(path.getParent())) {
                System.out.println("文件目录不存在！写入失败！");
                return;
            }
            FSDataOutputStream fsDataOutputStream = fs.create(path);
            inputStream = new FileInputStream(file);
            byte[] b = new byte[2048];
            int len = 0;
            while ((len = inputStream.read(b)) != -1) {
                fsDataOutputStream.write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
