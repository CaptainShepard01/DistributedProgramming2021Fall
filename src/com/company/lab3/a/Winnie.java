package com.company.lab3.a;

public class Winnie implements Runnable{
    HoneyPot honeyPot;

    public Winnie(HoneyPot honeyPot) {
        this.honeyPot = honeyPot;
    }

    @Override
    public void run() {
        while(true) {
            try {
                honeyPot.emptyPot();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(honeyPot.getTimesDrink() == 10){
                break;
            }
        }
    }
}
