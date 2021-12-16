package com.company.Module2.Exam;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;

public class ServerRMITask1 {
    public static void main(String[] args) throws RemoteException {
        Registry r = LocateRegistry.createRegistry(1099);
        StudentInterfaceRMI server = new StudentDaoRMI();

        server.addStudent(new Student(1, "Anton", "Balykov", LocalDate.parse("2001-05-05"), "Address", "4546778", Student.Faculty.FKNK, "3", "EPS-31"));
        server.addStudent(new Student(2, "John", "Smith", LocalDate.parse("2005-05-05"), "Address1", "4546778678", Student.Faculty.FKNK, "2", "EPS-32"));
        server.addStudent(new Student(3, "Frank", "Jakarta", LocalDate.parse("1999-05-05"), "Address2", "5686579675", Student.Faculty.FIZ, "3", "FIZ-1"));
        server.addStudent(new Student(3, "Frank", "Jakarta", LocalDate.parse("2002-05-05"), "Address2", "5686579675", Student.Faculty.FIZ, "3", "FIZ-2"));


        r.rebind("StudentInterface", server);
        System.out.println("Server started!");
    }
}
