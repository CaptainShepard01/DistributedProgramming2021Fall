package com.company.lab3.b;

public class Client implements Runnable {
    Barbershop barbershop;
    String name;
    public static int incr = 0;

    public Client(Barbershop barbershop) {
        this.barbershop = barbershop;
        this.name = "Client #" + incr++;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        while (!barbershop.isEndOfTheDay()) {
            try {
                barbershop.clientLogic();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
