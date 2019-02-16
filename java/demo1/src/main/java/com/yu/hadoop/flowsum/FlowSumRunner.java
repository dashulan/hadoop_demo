package com.yu.hadoop.flowsum;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.junit.Test;


//这是job描述和提交类的规范写法
public class FlowSumRunner extends Configured implements Tool {


    public int run(String[] strings) throws Exception {

        Configuration conf =  new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(FlowSumRunner.class);

        job.setMapperClass(FlowSumMapper.class);
        job.setReducerClass(FlowSumReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job,new Path(strings[0]));
        FileOutputFormat.setOutputPath(job,new Path(strings[1]));


        return job.waitForCompletion(true)?0:1;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(),new FlowSumRunner(),args);
        System.exit(res);

    }
}
