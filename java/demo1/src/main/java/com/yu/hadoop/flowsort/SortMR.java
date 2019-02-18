package com.yu.hadoop.flowsort;

import com.sun.tools.javac.comp.Flow;
import com.yu.hadoop.flowsum.FlowBean;
import com.yu.hadoop.flowsum.FlowSumMapper;
import com.yu.hadoop.flowsum.FlowSumReducer;
import com.yu.hadoop.flowsum.FlowSumRunner;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

public class SortMR {


    public static class SortMapper extends Mapper<LongWritable, Text, FlowBean, NullWritable>{

        //拿到一行数据，且分出各字段，封装为一个flowbean，作为key输出
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();

            String[] fields = line.split("\\t+");

            String phoneNB = fields[0];
            long u_flow = Long.parseLong(fields[1]);
            long d_flow = Long.parseLong(fields[2]);

            context.write(new FlowBean(phoneNB,u_flow,d_flow),NullWritable.get());

        }
    }


    public static class SortRuducer extends Reducer<FlowBean, NullWritable, FlowBean, NullWritable>{

        @Override
        protected void reduce(FlowBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key,NullWritable.get());
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf =  new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(SortMR.class);

        job.setMapperClass(SortMapper.class);
        job.setReducerClass(SortRuducer.class);

        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(FlowBean.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));



        System.exit(job.waitForCompletion(true)?0:1);
    }

}
