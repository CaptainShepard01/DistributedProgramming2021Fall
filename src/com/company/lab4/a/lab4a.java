package com.company.lab4.a;

import java.io.IOException;
import java.io.RandomAccessFile;

import static com.company.lab4.a.FileInteractor.fileName;

public class lab4a {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileInteractor interactor = new FileInteractor();
        interactor.clearFile();

        FileInteractor.writeToFile(new Writer("Vasya", "uipuipuiipuip"));
        FileInteractor.writeToFile(new Writer("Petya", "sgdsagdsadg"));
        FileInteractor.writeToFile(new Writer("Vova", "hljjhkl;hj;k"));

        Writer writer1 = FileInteractor.findInFile("Vova", FileInteractor.Field.NAME);
        System.out.println(writer1);
        FileInteractor.writeToFile(new Writer("Grysha", "cvbmvcbmvcmbvcbm"));
        FileInteractor.writeToFile(new Writer("Semen", "+zXCZXCzCXzcxZXC"));

        Writer writer2 = FileInteractor.findInFile("sgdsagdsadg", FileInteractor.Field.PHONE);
        System.out.println(writer2);

        System.out.println();
        FileInteractor.readFile();
        System.out.println();
        FileInteractor.removeFromFile(new Writer("Grysha", "cvbmvcbmvcmbvcbm"));
        FileInteractor.readFile();

        /*String fileName = "E:\\Projects\\DistributedProgramming2021Fall\\WritersDatabase.bin";
        RandomAccessFile file = new RandomAccessFile(fileName, "r");
        Writer writer = new Writer("Toha", "Eislavabogu");
        writer.write(file);
        writer.read(file);
        System.out.println(writer);*/
    }
}
