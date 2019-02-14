package com.yu.hadoop.rpc;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;

import java.io.IOException;

public class Starter {


    public static void main(String[] args) throws IOException {

        RPC.Builder builder =  new RPC.Builder(new Configuration());

        builder.setBindAddress("192.168.1.112").setPort(10000).setProtocol(LoginServiceInterface.class).setInstance(new LoinsServiceImpl());

        Server server = builder.build();

        server.start();

    }
}
