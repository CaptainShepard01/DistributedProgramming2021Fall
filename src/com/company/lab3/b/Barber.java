package com.company.lab3.b;

public class Barber implements Runnable {
    Barbershop barbershop;

    public Barber(Barbershop barbershop) {
        this.barbershop = barbershop;
    }

    @Override
    public void run() {
        while (!barbershop.isEndOfTheDay()) {
            if (barbershop.getClients().isEmpty()) {
                System.out.println("Barber is sleeping.");
                while (barbershop.getClients().isEmpty()) {
                    try {
                        Thread.currentThread().sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    barbershop.trim(barbershop.getClients().remove(0));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("End of the day");
    }
}
