package com.company.Module1.lab4.b;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Garden {
    private Boolean[][] conditions;
    private int length, width;
    public String fileName = "GardenConditionsDatabase.bin";
    private BufferedWriter output;
    private int counter = 0;

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public Garden(int length, int width) {
        this.length = length;
        this.width = width;
        conditions = new Boolean[length][width];
        motherNature();
    }

    public void motherNature() {
        Random random = new Random();
        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < width; ++j) {
                w.lock();
                try {
                    conditions[i][j] = random.nextBoolean();
                } finally {
                    w.unlock();
                }
            }
        }
    }

    public void printGarden() {
        r.lock();
        try {
            for (Boolean[] row : conditions) {
                System.out.print("| ");
                for (boolean element : row) {
                    System.out.print((element ? 1 : 0) + " ");
                }
                System.out.println("|");
            }
        } finally {
            r.unlock();
        }
        System.out.println();
    }

    public void pourPlants() {
        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < width; ++j) {
                synchronized (conditions[i][j]) {
                    if (!conditions[i][j]){
                        w.lock();
                        try {
                            conditions[i][j] = true;
                        } finally {
                            w.unlock();
                        }
                    }
                }
            }
        }
    }

    public void writeToFile() {
        try {
            output = new BufferedWriter(new FileWriter(fileName, true));
            output.write("Garden condition #" + counter++ + ":\n");
            r.lock();
            try {
                for (Boolean[] row : conditions) {
                    output.write("| ");
                    for (boolean element : row) {
                        output.write((element ? 1 : 0) + " ");
                    }
                    output.write("|\n");
                }
                output.write("\n");
                output.close();
            } finally {
                r.unlock();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearFile() {
        try {
            output = new BufferedWriter(new FileWriter(fileName));
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
