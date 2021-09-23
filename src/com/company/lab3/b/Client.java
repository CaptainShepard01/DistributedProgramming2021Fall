package com.company.lab3.b;

public class Client implements Runnable {
    Barbershop barbershop;
    String name;
    public static int incr = 0;

    public Client(Barbershop barbershop) {
        this.barbershop = barbershop;
        this.name = "Clent #" + incr++;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        if(barbershop.isOccupied()){
            System.out.println(name + " is sleeping.");
        }
        while (barbershop.isOccupied()) {
            try {
                Thread.currentThread().sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
