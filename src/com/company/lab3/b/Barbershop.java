package com.company.lab3.b;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class Barbershop {
    public static int CUSTOMERS_NUMBER = 10;
    public static int incr = 0;

    public static volatile boolean occupied;
    public static boolean endOfTheDay;
    public static volatile BlockingQueue<Client> clients = new ArrayBlockingQueue<Client>(CUSTOMERS_NUMBER);

    public Barbershop() {
        this.occupied = false;
        this.endOfTheDay = false;
    }

    public void clientHasCome(Client client) {
        System.out.println(client.getName() + " has come.");
        synchronized (this) {
            clients.add(client);
            incr++;
            notifyAll();
        }
    }

    public static boolean isOccupied() {
        return occupied;
    }

    public static void setOccupied() {
        occupied = true;
    }

    public static boolean isEndOfTheDay() {
        return endOfTheDay;
    }

    public static BlockingQueue<Client> getClients() {
        return clients;
    }

    public static int getCustomersNumber() {
        return CUSTOMERS_NUMBER;
    }

    public void barberLogic() throws InterruptedException {
        if (clients.isEmpty()) {
            synchronized (this) {
                System.out.println("Barber is sleeping.");
                wait();
            }
        } else {
            trim(getClients().take());
        }
    }

    public void clientLogic() throws InterruptedException {
        while(isOccupied()){
            System.out.println(Thread.currentThread().getName() + " is sleeping.");
            wait();
        }
    }

    public void trim(Client client) throws InterruptedException {
        Random random = new Random();
        System.out.println("Trim of " + client.getName() + " started.");
        sleep(random.nextInt(2000));
        System.out.println("Trim of " + client.getName() + " finished.");
        if (incr == CUSTOMERS_NUMBER && clients.isEmpty()) {
            endOfTheDay = true;
        }
        synchronized (this) {
            notifyAll();
        }
    }
}

