package com.yu.hadoop.hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;
import sun.security.krb5.Config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HDFSUtil {

    private FileSystem fs = null;

    @Before
    public void init() throws IOException, URISyntaxException, InterruptedException {
        //读取classpath下的xx-site.xml配置文件，并解析其内容，封装到conf对象中去
        Configuration conf = new Configuration();

        //也可以在代码中对conf中的配置信息进行手动设置，会覆盖掉配置文件的读取的值
        conf.set("fs.defaultFS","hdfs://192.168.1.118:8020");

        //根据配置信息，去获取一个具体文件系统的客户端操作实例对象（有很多不同的文件系统，如本地文件系统实例，）
        fs = FileSystem.get(new URI("hdfs://192.168.1.118:8020/"),conf,"hadoop");
    }

    @Test
    public void upload() throws IOException, URISyntaxException, InterruptedException {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://192.168.1.118:8020");

        FileSystem fs = FileSystem.get(conf);


        Path dst = new Path("hdfs://192.168.1.118:8020/usr/qingshu.txt");
        FSDataOutputStream os = fs.create(dst);

        FileInputStream is = new FileInputStream("D:\\testDemoDir\\qingshu.txt");

        IOUtils.copy(is,os);
    }

    @Test
    public void upload2() throws IOException, URISyntaxException, InterruptedException {


        fs.copyFromLocalFile(new Path("D:\\testDemoDir\\qingshu.txt"),new Path("hdfs://192.168.1.118:8020//aa/bb/cc/qingshu2.txt"));


    }


    @Test
    public void mkdir() throws IOException, URISyntaxException, InterruptedException {

        fs.mkdirs(new Path("/aa/bb/cc"));
    }

    @Test
    public void download() throws InterruptedException, IOException, URISyntaxException {

        fs.copyToLocalFile(new Path("hdfs://192.168.1.118:8020/cccc.txt"),new Path("D:\\testDemoDir\\"));

    }

    @Test
    public void list() throws IOException, URISyntaxException, InterruptedException {


        //ListFileS 列出的是文件信息，而且提供递归遍历
        RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path("hdfs://192.168.1.118:8020/"),true);
        while(files.hasNext()){
            LocatedFileStatus file = files.next();
            Path filePath = file.getPath();
            String fileName = filePath.getName();
            System.out.println(fileName);
        }
        System.out.println("---------------------------------------");
        //ListStatus 可以列出文件和文件夹信息，但是不提供自带的递归遍历
        FileStatus[] listStatus =  fs.listStatus(new Path("hdfs://192.168.1.118:8020/"));
        for (FileStatus status:listStatus){
            String name = status.getPath().getName();
            System.out.println(name + (status.isDirectory()?" is dir":" is file"));
        }
    }

    @Test
    public void rm() throws InterruptedException, IOException, URISyntaxException {

        fs.delete(new Path("hdfs://192.168.1.118:8020/user"),true);
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();

        //也可以在代码中对conf中的配置信息进行手动设置，会覆盖掉配置文件的读取的值
        conf.set("fs.defaultFS","hdfs://192.168.1.118:8020");
        //根据配置信息，去获取一个具体文件系统的客户端操作实例对象（有很多不同的文件系统，如本地文件系统实例，）
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.1.118:8020/"),conf,"hadoop");


        FSDataInputStream is = fs.open(new Path("/hadoop-3.2.0.tar.gz"));

        FileOutputStream os = new FileOutputStream("D:\\testDemoDir\\hadoop.tar.gz");

        IOUtils.copy(is,os);


    }

}
