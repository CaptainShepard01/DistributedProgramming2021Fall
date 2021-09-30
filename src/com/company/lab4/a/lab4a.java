package com.company.lab4.a;

import java.io.IOException;

public class lab4a {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        FileInteractor.clearFile();

        Thread managerThread = new Thread(() -> {
            FileInteractor.writeToFile(new Writer("Vasya", "380504440000"));
            FileInteractor.writeToFile(new Writer("Petya", "380504441111"));
            FileInteractor.writeToFile(new Writer("Vova", "380505550000"));
            FileInteractor.writeToFile(new Writer("Grysha", "380505551111"));
            FileInteractor.writeToFile(new Writer("Semen", "380501234567"));
            FileInteractor.writeToFile(new Writer("Fedor", "380509871890"));
            FileInteractor.writeToFile(new Writer("Ivan", "380090123455"));
            FileInteractor.writeToFile(new Writer("Alexandr", "380965858444"));
            FileInteractor.removeByKey("Fedor", FileInteractor.Field.NAME);
            FileInteractor.removeByKey("Semen", FileInteractor.Field.NAME);
            FileInteractor.removeByKey("Vova", FileInteractor.Field.NAME);
            FileInteractor.removeByKey("380504441111", FileInteractor.Field.PHONE);
            FileInteractor.writeToFile(new Writer("Anton", "45684968896846"));
            FileInteractor.removeByKey("380965858444", FileInteractor.Field.PHONE);
            FileInteractor.removeByKey("380090123455", FileInteractor.Field.PHONE);
            FileInteractor.writeToFile(new Writer("Stepan", "5745754685468"));
        });
        managerThread.start();
        Thread.sleep(2);

        Thread nameFinderThread = new Thread(() -> {
            System.out.println("By name Vova: " + FileInteractor.findInFile("Vova", FileInteractor.Field.NAME));
            System.out.println("By name Grysha: " + FileInteractor.findInFile("Grysha", FileInteractor.Field.NAME));
            System.out.println("By name Fedor: " + FileInteractor.findInFile("Fedor", FileInteractor.Field.NAME));
            System.out.println("By name Alexandr: " + FileInteractor.findInFile("Alexandr", FileInteractor.Field.NAME));
        });

        Thread phoneFinderThread = new Thread(() -> {
            System.out.println("By phone 380504441111: " + FileInteractor.findInFile("380504441111", FileInteractor.Field.PHONE));
            System.out.println("By phone 380504440000: " + FileInteractor.findInFile("380504440000", FileInteractor.Field.PHONE));
            System.out.println("By phone 380509871890: " + FileInteractor.findInFile("380509871890", FileInteractor.Field.PHONE));
            System.out.println("By phone 380090123455: " + FileInteractor.findInFile("380090123455", FileInteractor.Field.PHONE));
        });

        nameFinderThread.start();
        phoneFinderThread.start();
    }
}
