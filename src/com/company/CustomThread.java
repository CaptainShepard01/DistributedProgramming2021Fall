package com.company;

public class CustomThread extends Thread{
    private String myName;

    public CustomThread(String myName) {
        this.myName = myName;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getId()+" = "+ Thread.currentThread().getName());
    }

    @Override
    public String toString() {
        return "CustomThread{" +
                "myName='" + myName + '\'' +
                "} " + super.toString();
    }
}
