package com.yu.hadoop.classloaddemo;

public class Bird implements FlyInterface {
    public void fly() {
        System.out.println("Bird Fly");
    }

    public static void main(String[] args) {
        new Bird().fly();
    }
}
