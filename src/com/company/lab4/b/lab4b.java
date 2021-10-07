package com.company.lab4.b;

public class lab4b {
    public static void main(String[] args) throws InterruptedException {
        Garden garden = new Garden(5, 5);
        garden.clearFile();

        Thread gardener = new Thread(() -> {
            for(int i = 0; i < 5; ++i){
                garden.pourPlants();
                System.out.println("Garden has been poured.\n");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread nature = new Thread(() -> {
            for(int i = 0; i < 10; ++i){
                garden.motherNature();
                System.out.println("Garden has been changed by Mother Nature.\n");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread monitor1 = new Thread(() -> {
            for(int i = 0; i < 5; ++i){
                garden.writeToFile();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread monitor2 = new Thread(() -> {
            for(int i = 0; i < 5; ++i){
                garden.printGarden();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        gardener.start();
        nature.start();
        monitor1.start();
        monitor2.start();

        gardener.join();
        nature.join();
        monitor1.join();
        monitor2.join();
    }
}
