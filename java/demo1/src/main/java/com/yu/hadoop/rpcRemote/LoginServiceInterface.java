package com.yu.hadoop.rpcRemote;

public interface LoginServiceInterface {
    public static final long versionId=1L;
    public String login(String username, String password);

}
