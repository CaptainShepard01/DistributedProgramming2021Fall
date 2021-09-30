package com.company.lab4.b;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Garden {
    private static Boolean[][] conditions;
    private static int length, width;
    public static String fileName = "GardenConditionsDatabase.bin";
    private static BufferedWriter output;
    private static int counter = 0;

    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private static final Lock r = rwl.readLock();
    private static final Lock w = rwl.writeLock();

    public Garden(int length, int width) {
        Garden.length = length;
        Garden.width = width;
        conditions = new Boolean[length][width];
        motherNature();
    }

    public static void motherNature() {
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

    public static void printGarden() {
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

    public static void pourPlants() {
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

    public static void writeToFile() {
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

    public static void clearFile() {
        try {
            output = new BufferedWriter(new FileWriter(fileName));
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
