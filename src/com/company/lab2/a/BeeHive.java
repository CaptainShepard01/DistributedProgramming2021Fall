package com.company.lab2.a;

public class BeeHive implements Runnable {
    private SharedValue forestInfo;
    private Forest currentForest;
    private int myRow = -1;

    public BeeHive(SharedValue forestInfo, Forest forest) {
        this.forestInfo = forestInfo;
        currentForest = forest;
    }

    private boolean setRow(){
        forestInfo.increment();
        if (forestInfo.getLastOccupiedCellRow() < currentForest.getRows())
            myRow = forestInfo.getLastOccupiedCellRow();
        else {
            System.out.println(Thread.currentThread().getName() + " end ");
            return false;
        }

        System.out.println(Thread.currentThread().getName() + " is on row number " + myRow);
        return true;
    }

    private boolean searchInRow(boolean[] cellsRow){
        for (int i = 0; i < cellsRow.length; ++i) {
            if (cellsRow[i] == true) {
                forestInfo.setWinniePosition(myRow, i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        if (forestInfo.isWinnieFound()) {
            return;
        }
        //System.out.println(Thread.currentThread().getName() + " start ");

        while (!forestInfo.isWinnieFound()) {
            if(!setRow())
                break;

            if(searchInRow(currentForest.getRow(myRow)))
                break;

            if (!forestInfo.isWinnieFound()) {
                try {
                    Thread.currentThread().sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //System.out.println(Thread.currentThread().getName() + " end ");
    }
}
