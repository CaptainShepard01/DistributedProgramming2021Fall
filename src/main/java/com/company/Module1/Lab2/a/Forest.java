package com.company.Module1.Lab2.a;

public class Forest {
    private static int rows, columns;
    private static boolean[][] cells;

    public Forest(int rows, int columns) {
        Forest.rows = rows;
        Forest.columns = columns;

        cells = new boolean[rows][columns];

    }

    public static void printForest(){
        for (var cellsRow:cells) {
            for (var cell:cellsRow) {
                System.out.print((cell?"1":"0") + " ");
            }
            System.out.println();
        }
    }

    public static void randomizePositionWinnieThePooh(){
        int randomRow = (int)(Math.random()*(rows-1));
        int randomColumn = (int)(Math.random()*(columns-1));

        cells[randomRow][randomColumn] = true;
    }

    public boolean[] getRow(int i){
        return cells[i];
    }

    public static int getRows() {
        return rows;
    }
}
