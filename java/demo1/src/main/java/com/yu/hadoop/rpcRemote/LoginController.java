package com.yu.hadoop.rpcRemote;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

public class LoginController {

    public static void main(String[] args) throws IOException {
        LoginServiceInterface proxy = RPC.getProxy(LoginServiceInterface.class,1L,
                new InetSocketAddress("192.168.1.112",10000),new Configuration());
        String result = proxy.login("dashulan","sss");
        System.out.println(result);

    }
}
