package com.company.lab3.b;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

public class Barbershop {
    public static int CUSTOMERS_NUMBER = 10;
    public static int incr = 0;

    public static volatile boolean occupied;
    public static boolean endOfTheDay;
    public static volatile ArrayList<Client> clients = new ArrayList<Client>();

    public Barbershop() {
        this.occupied = false;
        this.endOfTheDay = false;
    }

    public static void clientHasCome(Client client) {
        System.out.println("Client " + client.getName() + " has come.");
        clients.add(client);
        incr++;
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

    public static ArrayList<Client> getClients() {
        return clients;
    }

    public static int getCustomersNumber() {
        return CUSTOMERS_NUMBER;
    }

    public static void trim(Client client) throws InterruptedException {
        Random random = new Random();
        occupied = true;
        System.out.println("Trim of " + client.getName() + " started.");
        Thread.currentThread().sleep(random.nextInt(2000));
        System.out.println("Trim of " + client.getName() + " finished.");
        clients.remove(client);
        if (incr == CUSTOMERS_NUMBER && clients.isEmpty()) {
            endOfTheDay = true;
        }
        occupied = false;
    }
}

