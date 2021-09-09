package com.company.lab1.b;

public class CustomThread implements Runnable {
    private final SharedValue sharedValue;
    private final int value;

    public CustomThread(SharedValue sharedValue, int value) {
        this.sharedValue = sharedValue;
        this.value = value;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start " + value);
        sharedValue.incrementSemaphore();

        while (!Thread.currentThread().isInterrupted()) {
            sharedValue.modify(value);
            try {
                Thread.sleep(0, 2);
            } catch (InterruptedException e) {
                break;
            }
        }

        sharedValue.decrementSemaphore();
        System.out.println(Thread.currentThread().getName() + " end ");
    }
}

