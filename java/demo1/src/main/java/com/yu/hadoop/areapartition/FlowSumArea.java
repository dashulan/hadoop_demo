package com.yu.hadoop.areapartition;

import com.yu.hadoop.flowsum.FlowBean;
import com.yu.hadoop.flowsum.FlowSumReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

/**
 * 对流量原始日志进行流量统计，将不同省份的用户统计结果输出到不同文件
 * 需要进行自定义改造两个机制
 * 1.改造分区的逻辑，自定义一个partitioner
 * 2.自定义reducer task的并发任务数
 * @Author dashulan
 * @Date 15:38 2019/2/18
 **/
public class FlowSumArea {

    public static class FlowSumAreaMapper extends Mapper<LongWritable, Text, Text, FlowBean>{

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            //切分成各个字段
            String[] fields = StringUtils.split(line,' ');

            String phoneNB = fields[0];
            long u_flow = Long.parseLong(fields[1]);
            long d_flow = Long.parseLong(fields[2]);

            //封装数据为kv并输出
            context.write(new Text(phoneNB),new FlowBean(phoneNB,u_flow,d_flow));
        }
    }


    public static class FlowSumAreaReducer extends Reducer<Text, FlowBean, Text, FlowBean>{

        @Override
        protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
            long up_flow_counter = 0;
            long d_flow_counter = 0;

            for (FlowBean bean :values){
                up_flow_counter += bean.getUp_flow();
                d_flow_counter += bean.getD_flow();
            }

            //bean 写的时候调用toString（）方法
            context.write(key,new FlowBean(key.toString(),up_flow_counter,d_flow_counter));
        }


    }

    public static void main(String[] args) throws Exception {
        Configuration conf= new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(FlowSumArea.class);

        job.setMapperClass(FlowSumAreaMapper.class);
        job.setReducerClass(FlowSumAreaReducer.class);

        //设置我们自定义的分组逻辑定义
        job.setPartitionerClass(AreaPartitioner.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        //设置reduce的任务并发数，和partitioner数量一致
        job.setNumReduceTasks(1);


        System.exit(job.waitForCompletion(true)?0:1);



    }



}
