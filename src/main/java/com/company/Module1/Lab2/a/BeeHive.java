package com.company.Module1.Lab2.a;

public class BeeHive implements Runnable {
    private final SharedValue forestInfo;
    private final Forest currentForest;
    private int myRow = -1;

    public BeeHive(SharedValue forestInfo, Forest forest) {
        this.forestInfo = forestInfo;
        currentForest = forest;
    }

    private boolean setRow() {
        forestInfo.increment();
        if (SharedValue.getLastOccupiedCellRow() < Forest.getRows())
            myRow = SharedValue.getLastOccupiedCellRow();
        else {
            //System.out.println(Thread.currentThread().getName() + " end ");
            return false;
        }

        //System.out.println(Thread.currentThread().getName() + " is on row number " + myRow);
        return true;
    }

    private boolean searchInRow(boolean[] cellsRow) {
        for (int i = 0; i < cellsRow.length; ++i) {
            if (cellsRow[i]) {
                forestInfo.setWinniePosition(myRow, i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        if (SharedValue.isWinnieFound()) {
            return;
        }
        //System.out.println(Thread.currentThread().getName() + " start ");

        while (!SharedValue.isWinnieFound()) {
            if (!setRow())
                break;

            if (searchInRow(currentForest.getRow(myRow)))
                break;

            if (!SharedValue.isWinnieFound()) {
                for (int i = 0; i < 1e6; ++i) ;
            }
        }

        //System.out.println(Thread.currentThread().getName() + " end ");
    }
}
