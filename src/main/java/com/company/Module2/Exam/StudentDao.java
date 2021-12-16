package com.company.Module2.Exam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDao{
    List<Student> students;

    public StudentDao() {
        students = new ArrayList<>();
    }

    public synchronized List<Student> studentsBirthAfterDate(LocalDate from) {
        List<Student> resultList = new ArrayList<>();

        for (Student student : students) {
            if (student.getBirthDate().compareTo(from) > 0) {
                resultList.add(student);
            }
        }

        return resultList;
    }

    public synchronized List<Student> studentsFromGroup(String group) {
        List<Student> resultList = new ArrayList<>();

        for (Student student : students) {
            if (student.getGroup().equals(group)) {
                resultList.add(student);
            }
        }

        return resultList;
    }

    public synchronized List<Student> studentsFromFaculty(Student.Faculty faculty) {
        List<Student> resultList = new ArrayList<>();

        for (Student student : students) {
            if (student.getFaculty().equals(faculty)) {
                resultList.add(student);
            }
        }

        return resultList;
    }

    public synchronized List<Student> studentsFromCourse(String course) {
        List<Student> resultList = new ArrayList<>();

        for (Student student : students) {
            if (student.getCourse().equals(course)) {
                resultList.add(student);
            }
        }

        return resultList;
    }

    public synchronized List<List<Student>> studentsFromAllFaculties() {
        List<List<Student>> resultList = new ArrayList<>();

        for (Student.Faculty faculty : Student.Faculty.values()) {
            List<Student> currentList = new ArrayList<>();
            for (Student student : students) {
                if (student.getFaculty().equals(faculty)) {
                    currentList.add(student);
                }
            }
            resultList.add(currentList);
        }

        return resultList;
    }

    public synchronized void addStudent(Student student) {
        students.add(student);
    }
}
