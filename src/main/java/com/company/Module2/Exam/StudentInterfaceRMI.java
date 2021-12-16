package com.company.Module2.Exam;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

public interface StudentInterfaceRMI extends Remote {
    List<Student> studentsBirthAfterDate(LocalDate from) throws RemoteException;
    List<Student> studentsFromGroup(String group) throws RemoteException;
    List<Student> studentsFromFaculty(Student.Faculty faculty) throws RemoteException;
    List<Student> studentsFromCourse(String course) throws RemoteException;
    List<List<Student>> studentsFromAllFaculties() throws RemoteException;
    void addStudent(Student student) throws RemoteException;
}
