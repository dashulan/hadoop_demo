package com.yu.hadoop.rpc;

public class LoinsServiceImpl implements LoginServiceInterface {


    public String login(String username, String password) {
        return username + "logged in successfully";
    }
}
