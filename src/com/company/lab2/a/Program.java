package com.company.lab2.a;

import javax.swing.*;

public class Program {
    public static void main(String[] args) {
        Forest forest1 = new Forest(15, 15);

        forest1.randomizePositionWinnieThePooh();
        forest1.printForest();
    }
}
