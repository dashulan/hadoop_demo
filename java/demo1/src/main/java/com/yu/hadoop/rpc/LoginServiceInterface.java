package com.yu.hadoop.rpc;

public interface LoginServiceInterface {

    public static final long versionId=1L;
    public String login(String username,String password);

}
