package com.yu.hadoop.flowsum;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class FlowBean implements WritableComparable<FlowBean> {
    
    private String phoneNB;
    private long up_flow;
    private long d_flow;
    private long s_flow;

    //在反序列化时，反射机制需要调用一个空参的构造函数，所以显示定义了一个空参的构造函数
    public FlowBean() {
    }

    //为了对象数据初始化方便，将入一个带参的构造函数
    public FlowBean(String phoneNB, long up_flow, long d_flow) {
        this.phoneNB = phoneNB;
        this.up_flow = up_flow;
        this.d_flow = d_flow;
        this.s_flow = this.up_flow + this.d_flow;
    }

    public String getPhoneNB() {
        return phoneNB;
    }

    public void setPhoneNB(String phoneNB) {
        this.phoneNB = phoneNB;
    }

    public long getUp_flow() {
        return up_flow;
    }

    public void setUp_flow(long up_flow) {
        this.up_flow = up_flow;
    }

    public long getD_flow() {
        return d_flow;
    }

    public void setD_flow(long d_flow) {
        this.d_flow = d_flow;
    }

    public long getS_flow() {
        return s_flow;
    }

    public void setS_flow(long s_flow) {
        this.s_flow = s_flow;
    }

    
    /**
     * 将对象序列化到流中
     * @Author dashulan
     * @Date 22:29 2019/2/16
     **/

    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(phoneNB);
        dataOutput.writeLong(up_flow);
        dataOutput.writeLong(d_flow);
        dataOutput.writeLong(s_flow);
    }

    /**
     * 从数据流中反序列化出对象的数据(涉及到反射)
     * @Author dashulan
     * @Date 22:30 2019/2/16
     **/

    public void readFields(DataInput dataInput) throws IOException {
        phoneNB = dataInput.readUTF();
        up_flow = dataInput.readLong();
        d_flow = dataInput.readLong();
        s_flow = dataInput.readLong();
    }

    @Override
    public String toString() {
        /*return "FlowBean{" +
                "phoneNB='" + phoneNB + '\'' +
                ", up_flow=" + up_flow +
                ", d_flow=" + d_flow +
                ", s_flow=" + s_flow +
                '}';*/
        return  up_flow + "\t" + d_flow + "\t" +s_flow;
    }

    public int compareTo(FlowBean o) {
        return this.s_flow > o.s_flow ?-1:1;
    }


}
