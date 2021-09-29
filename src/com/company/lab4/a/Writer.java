package com.company.lab4.a;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.StringJoiner;

public class Writer implements Serializable {
    private String name;
    private String phone;

    public Writer(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Writer.class.getSimpleName() + "[", "]")
                .add(name)
                .add(phone)
                .toString();
    }

    void read(RandomAccessFile raf) throws IOException {
        char[] temp = new char[15];
        for (int i = 0; i < temp.length; i++)
            temp[i] = raf.readChar();
        name = new String(temp);
        temp = new char[15];
        for (int i = 0; i < temp.length; i++)
            temp[i] = raf.readChar();
        phone = new String(temp);
    }

    void write(RandomAccessFile raf) throws IOException {
        StringBuffer sb = new StringBuffer(name);

        sb.setLength(15);
        raf.writeChars(sb.toString());

        sb = new StringBuffer(phone);

        sb.setLength(15);
        raf.writeChars(sb.toString());
    }

    public boolean isEqual(Writer other){
        return this.name.equals(other.name) && this.phone.equals(other.phone);
    }
}
