package com.company.lab4.a;

import java.io.*;
import java.util.ArrayList;

public class FileInteractor {
    public static String fileName = "WritersDatabase.bin";
    private static ObjectOutputStream output;

    public enum Field {
        PHONE,
        NAME
    }


    public FileInteractor() throws IOException {
        output = new ObjectOutputStream(new FileOutputStream(fileName, true));
    }

    public static void writeToFile(Writer writer) {
        try {
            output.writeObject(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Writer findInFile(String key, Field field) {
        try {
            FileInputStream istream = new FileInputStream(fileName);
            ObjectInputStream input = new ObjectInputStream(istream);

            boolean isFound = false;
            Writer writer = new Writer("", "");
            while (!isFound && istream.available() > 0) {
                writer = (Writer) input.readObject();
                switch (field) {
                    case NAME: {
                        if (writer.getName().equals(key)) {
                            isFound = true;
                            break;
                        }
                    }
                    case PHONE: {
                        if (writer.getPhone().equals(key)) {
                            isFound = true;
                            break;
                        }
                    }
                }
            }
            return writer;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void clearFile() throws IOException {
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("");
        writer.close();
        output = new ObjectOutputStream(new FileOutputStream(fileName, true));
    }

    private static ArrayList<Writer> getAllItems(FileInputStream istream, ObjectInputStream input) throws IOException, ClassNotFoundException {
        ArrayList<Writer> array = new ArrayList<>();
        while (istream.available() > 0) {
            Writer buffer = (Writer) input.readObject();
            array.add(buffer);
        }
        return array;
    }

    public static void removeFromFile(Writer writer) throws IOException, ClassNotFoundException {
        FileInputStream istream = new FileInputStream(fileName);
        ObjectInputStream input = new ObjectInputStream(istream);

        ArrayList<Writer> array = getAllItems(istream, input);

        int index = -1;
        for (int i = 0; i < array.size(); ++i) {
            if (array.get(i).isEqual(writer)) {
                index = i;
            }
        }

        array.remove(index);

        clearFile();

        for (Writer item : array) {
            output.writeObject(item);
        }
    }

    public static void readFile() throws IOException, ClassNotFoundException {
        FileInputStream istream = new FileInputStream(fileName);
        ObjectInputStream input = new ObjectInputStream(istream);

        while (istream.available() > 0) {
            Writer buffer = (Writer) input.readObject();
            System.out.println(buffer);
        }
    }
}
