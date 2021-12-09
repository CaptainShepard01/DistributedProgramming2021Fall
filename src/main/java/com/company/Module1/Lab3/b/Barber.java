package com.company.Module1.Lab3.b;

public class Barber implements Runnable {
    Barbershop barbershop;

    public Barber(Barbershop barbershop) {
        this.barbershop = barbershop;
    }

    @Override
    public void run() {
        while (!barbershop.isEndOfTheDay()) {
            try {
                barbershop.barberLogic();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("End of the day.");
    }
}
