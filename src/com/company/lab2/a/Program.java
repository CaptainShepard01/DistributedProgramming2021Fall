package com.company.lab2.a;

import java.time.Instant;

public class Program {
    static int NUMBER_OF_THREADS = 20;
    public static Instant start;

    public static void main(String[] args) {
        Forest forest = new Forest(500, 500);
        SharedValue forestInfo = new SharedValue();

        Forest.randomizePositionWinnieThePooh();
        startThreads(forestInfo, forest);
    }

    private static void startThreads(SharedValue forestInfo, Forest forest) {
        Thread[] threads = new Thread[NUMBER_OF_THREADS];

        start = Instant.now();

        for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
            threads[i] = new Thread(new BeeHive(forestInfo, forest));
            threads[i].setName("BeeHive " + i);
            threads[i].start();
        }
    }
}
