package com.company.Module1.lab3.a;

public class lab3a {
    public static HoneyPot honeyPot = new HoneyPot(10);
    public static int numberOfBees = 8;

    public static void main(String[] args) throws InterruptedException {
        Thread winnie = new Thread(new Winnie(honeyPot));
        winnie.setName("Winnie the Pooh");
        winnie.start();

        for(int i=0;i<numberOfBees;++i){
            Thread bee = new Thread(new Bee(honeyPot));
            bee.setName("Bee #" + i);
            bee.start();
        }

        winnie.join();
    }
}
