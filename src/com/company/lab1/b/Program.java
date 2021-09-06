package com.company.lab1.b;

class SharedValue{
    private int value;

    public SharedValue(int value) {
        this.value = value;
    }

    public synchronized int getValue() {
        return value;
    }

    public synchronized void setValue(int value) {
        this.value = value;
    }

    public synchronized void increment(int value){
        this.value+=value;
        System.out.println(Thread.currentThread().getName() + " : " +this.value);

    }

    @Override
    public String toString() {
        return "SharedValue{" +
                "value=" + value +
                '}';
    }
}

class MyTh implements Runnable {
    private SharedValue sharedValue;
    private int value;

    public MyTh(SharedValue sharedValue, int value) {
        this.sharedValue = sharedValue;
        this.value = value;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start ");
        while (!Thread.interrupted()){
            sharedValue.increment(value);
        }
        System.out.println(Thread.currentThread().getName() + " end ");
    }
}

public class Program {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " start ");
        SharedValue sharedValue = new SharedValue(10);
        Thread th1 = new Thread( new MyTh(sharedValue,-1));
        Thread th2 = new Thread( new MyTh(sharedValue,1));
        th1.start();
        th2.start();
        Thread.sleep(50);
        th1.interrupt();
        th2.interrupt();
        System.out.println(Thread.currentThread().getName() + " end ");
    }

}