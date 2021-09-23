package com.company.lab2.a;

import java.time.Duration;
import java.time.Instant;

import static com.company.lab2.a.lab2a.start;

public class SharedValue {
    private static int lastOccupiedCellRow = -1;
    private static int WinnieThePoohRow = -1, WinnieThePoohColumn = -1;
    private static boolean winnieFound = false;

    public synchronized void increment(){
        lastOccupiedCellRow++;
    }

    public synchronized void setWinniePosition(int row, int column){
        WinnieThePoohRow = row;
        WinnieThePoohColumn = column;
        winnieFound = true;

        printWinniePosition();
    }

    public static boolean isWinnieFound() {
        return winnieFound;
    }

    public static int getLastOccupiedCellRow() {
        return lastOccupiedCellRow;
    }

    public void printWinniePosition(){
        System.out.println("\nWinnie has been destroyed at position: (" +
                WinnieThePoohRow +
                ", " +
                WinnieThePoohColumn +
                ")\n" +
                "By thread " +
                Thread.currentThread().getName());
        System.out.printf("Time elapsed: %,d nanos", Duration.between(start, Instant.now()).toNanos());
    }
}
