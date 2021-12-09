package com.company.Module1.Lab3.a;

public class HoneyPot {
    public static boolean full;
    public static int volume = 0;
    public static int capacity;
    public static int timesDrink = 0;

    public static boolean isFull() {
        return full;
    }

    public HoneyPot(int capacity) {
        this.capacity = capacity;
    }

    public static int getTimesDrink() {
        return timesDrink;
    }

    public void fillPot() throws InterruptedException {
        synchronized (this) {
            if(full){
                wait();
            }
            if(timesDrink == 10){
                return;
            }
            System.out.println(Thread.currentThread().getName() + " has brought honey.");
            volume++;
            if(volume == capacity){
                full = true;
            }
            if(full){
                notifyAll();
            }
        }
    }

    public void emptyPot() throws InterruptedException {
        synchronized (this){
            if(!full){
                wait();
            }
            volume = 0;
            timesDrink++;
            Thread.currentThread().sleep(500);
            System.out.println(Thread.currentThread().getName() + " has drunk all the honey.");
            full = false;
            notifyAll();
        }
    }
}
