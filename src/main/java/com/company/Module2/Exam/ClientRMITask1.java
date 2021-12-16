package com.company.Module2.Exam;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;

public class ClientRMITask1 {
    public static void main(String[] args) throws
            MalformedURLException, NotBoundException, RemoteException {
        StudentInterfaceRMI studentInterface = (StudentInterfaceRMI) Naming.lookup("//127.0.0.1/StudentInterface");

        System.out.printf("Students from FKNK: %s\n", studentInterface.studentsFromFaculty(Student.Faculty.FKNK));
        System.out.printf("Students from FIZ: %s\n", studentInterface.studentsFromFaculty(Student.Faculty.FIZ));

        System.out.printf("Students of all faculties: %s\n", studentInterface.studentsFromAllFaculties());

        System.out.printf("Students who was born after date 2000-01-01: %s\n", studentInterface.studentsBirthAfterDate(LocalDate.parse("2000-01-01")));

        System.out.printf("Students from group EPS-31: %s\n", studentInterface.studentsFromGroup("EPS-31"));
    }
}
