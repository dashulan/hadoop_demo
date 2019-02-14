package com.yu.hadoop.mr.wordcount;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

//4个泛型中，前两个是指定mapper输入数据的类型，KEYIN是输入KEY的类型，VALUEIN是输入value的类型
//map,reduce的数据输入输出都是以key-value对的形式封装的
//默认情况下，框架传递给我们的mapper的输入数据中，key是要处理的文本中一行的起始偏移量，这一行的值作为value
/*   1.
public class WCReduce extends Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT> {
}
*/

/*  2.因为要走网络，所以要序列化，LONG,STRING是内存数据，可以序列化，但是是由java提供的附加了很多信息，为了减少传输量，hadoop自己实现了序列化机制
public class WCReduce extends Mapper <Long, String, String, Long>{

}
*/

public class WCReduce extends Mapper <LongWritable, Text, Text, LongWritable>{

    //mapReduce框架每读取一行数据就调用一次该方法
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //具体业务逻辑就卸载这个方法体中，并且我们业务要处理的数据已经被框架传递进来，在方法的参数中key-value
        //key是这一行数据的起始偏移，value是这一行的文本内容
    }
}

