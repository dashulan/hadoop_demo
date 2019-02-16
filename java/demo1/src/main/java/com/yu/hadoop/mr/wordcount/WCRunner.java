package com.yu.hadoop.mr.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 用来描述一个特定的作业
 * 比如，该作业使用哪个类作为逻辑处理中的map，哪个作为reduce
 * 还可以指定作业要处理的数据所在路径
 * 还可以指定该作业输出的结果放到哪个路径
 * @Author dashulan
 * @Date 23:26 2019/2/14
 **/

public class WCRunner {

    public static void main(String[] args) throws Exception {

        Configuration conf =  new Configuration();

        conf.set("fs.defaultFS","hdfs://192.168.1.118:8020");
        conf.set("mapreduce.framework.name","yarn");
        conf.set("hadoop.tmp.dir","/opt/data/tmp");
        conf.set("yarn.nodemanager.aux-services","mapreduce_shuffle");
        Job job = Job.getInstance(conf);

        job.setJobName("dashulanのremwi");

        //设置整个job所用的那些类在哪个jar包
        job.setJarByClass(WCRunner.class);


        //本job使用的mapper和reducer的类
        job.setMapperClass(WCMapper.class);
        job.setReducerClass(WCReducer.class);

        //指定reduce的输出数据kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //指定mapper的输出数据kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        //指定要处理的输入数据存放路径
        FileInputFormat.setInputPaths(job,new Path("hdfs://192.168.1.118:8020/wc/input/"));

        //
        FileOutputFormat.setOutputPath(job,new Path("hdfs://192.168.1.118:8020/wc/output2/"));

        System.exit(job.waitForCompletion(true)?0:1);
    }





}
