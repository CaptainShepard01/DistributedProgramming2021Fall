package com.company.lab3.b;

import java.util.Random;

public class lab3b {
    public static Barbershop barbershop = new Barbershop();
    public static Thread barber;

    public static void init() throws InterruptedException {
        Random random = new Random();
        barber = new Thread(new Barber(barbershop));
        barber.start();
        for (int i = 0; i < barbershop.getCustomersNumber(); ++i) {
            Client newClient = new Client(barbershop);
            Thread newThread = new Thread(newClient);
            newThread.start();
            barbershop.clientHasCome(newClient);
            Thread.currentThread().sleep(random.nextInt(2000));
        }
        barber.join();
    }

    public static void main(String[] args) throws InterruptedException {
        init();
    }
}
