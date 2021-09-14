package com.company.lab2.a;

public class Program {
    static int NUMBER_OF_THREADS = 20;

    public static void main(String[] args) {
        Forest forest = new Forest(1500, 1500);
        SharedValue forestInfo = new SharedValue();

        forest.randomizePositionWinnieThePooh();
        startThreads(forestInfo, forest);
    }

    private static void startThreads(SharedValue forestInfo, Forest forest) {
        Thread[] threads = new Thread[NUMBER_OF_THREADS];
        for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
            threads[i] = new Thread(new BeeHive(forestInfo, forest));
            threads[i].start();
        }
    }
}
