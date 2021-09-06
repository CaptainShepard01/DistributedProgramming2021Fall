package com.company;

public class Main {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getId()+" = "+ Thread.currentThread().getName());
        CustomThread customThread = new CustomThread("first");
        customThread.start();
    }
}
