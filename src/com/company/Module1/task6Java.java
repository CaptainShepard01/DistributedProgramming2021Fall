/*Свободная  касса.  В  ресторане  быстрого  обслуживания  есть  несколько
  касс. Посетители стоят в очереди в конкретную кассу, но могут перейти
  в другую очередь при уменьшении или исчезновении там очереди.*/

package com.company.Module1;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

class Cass extends Thread {
    private int numberOfCustomers;
    private ArrayBlockingQueue<Thread> customers;
    private final int cassNumber;
    public static int numberOfClients = 0;

    public Cass(int cassNumber) {
        this.numberOfCustomers = 0;
        this.customers = new ArrayBlockingQueue<>(50);
        this.cassNumber = cassNumber;
    }

    public void addCustomer(Customer newCustomer) {
        try {
            customers.put(newCustomer);
            numberOfCustomers++;
            System.out.println("Customer " + newCustomer.getId() + " is in queue of Cass " + cassNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void serveCustomer() throws InterruptedException {
        if (numberOfCustomers > 0) {
            Thread servedCustomer = customers.take();
            numberOfCustomers--;
            numberOfClients--;
            servedCustomer.interrupt();
            System.out.println("Customer " + servedCustomer.getId() + " was served at Cass " + cassNumber);
        }
    }

    public void removeCustomer(Customer customer, Cass newCass) {
        if (customers.remove(customer)) {
            System.out.println("Customer " + customer.getId() + " has left queue of Cass " + cassNumber);
            numberOfCustomers--;

            newCass.addCustomer(customer);
        }
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                sleep(100);
                serveCustomer();
            } catch (InterruptedException e) {
                System.out.println("Cass " + cassNumber + " has ended its work.");
                interrupt();
            }
        }
    }
}

class Customer extends Thread {
    private final long customerName;
    private Cass cass;
    private final ArrayList<Cass> casses;

    public Customer(Cass cass, ArrayList<Cass> casses, long customerName) {
        this.cass = cass;
        this.casses = casses;
        this.customerName = customerName;
    }

    @Override
    public long getId() {
        return customerName;
    }

    public void changeCass(Cass newCass) {
        this.cass.removeCustomer(this, newCass);
        this.cass = newCass;
    }

    @Override
    public void run() {
        cass.addCustomer(this);
        while (!isInterrupted()) {
            try {
                sleep(200);
                for (Cass cass : casses) {
                    if (cass.getNumberOfCustomers() == 0) {
                        changeCass(cass);
                        break;
                    }
                }
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }
}

public class task6Java {
    static final int NUMBEROFCASSES = 2;
    static final int NUMBEROFCUSTOMERS = 5;

    public static void main(String[] args) {
        Random random = new Random();
        ArrayList<Cass> casses = new ArrayList<>();
        Cass.numberOfClients = NUMBEROFCUSTOMERS;

        for (int i = 0; i < NUMBEROFCASSES; ++i) {
            Cass cass = new Cass(i);
            casses.add(cass);
        }

        for (Cass cass : casses) {
            cass.start();
        }

        for (int i = 0; i < NUMBEROFCUSTOMERS; ++i) {
            int currentCassNumber = random.nextInt(NUMBEROFCASSES);
            Thread customer = new Customer(casses.get(currentCassNumber), casses, i);
            customer.start();
        }

        while (Cass.numberOfClients != 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Cass cass : casses) {
            cass.interrupt();
        }
    }
}
