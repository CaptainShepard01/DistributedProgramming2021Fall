package com.company.Module2.Exam;

import java.rmi.Remote;
import java.time.LocalDate;
import java.util.List;

public interface StudentInterface extends Remote {
    List<Student> studentsBirthAfterDate(LocalDate from);
    List<Student> studentsFromGroup(String group);
    List<Student> studentsFromFaculty(Student.Faculty faculty);
    List<Student> studentsFromCourse(String course);
    List<List<Student>> studentsFromAllFaculties();
    void addStudent(Student student);
}
