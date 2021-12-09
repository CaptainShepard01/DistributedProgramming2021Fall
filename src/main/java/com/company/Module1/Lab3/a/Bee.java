package com.company.Module1.Lab3.a;

public class Bee implements Runnable{
    HoneyPot honeyPot;

    public Bee(HoneyPot honeyPot) {
        this.honeyPot = honeyPot;
    }

    @Override
    public void run() {
        while(true){
            try {
                if(honeyPot.getTimesDrink() == 10){
                    break;
                }
                honeyPot.fillPot();
                Thread.currentThread().sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
