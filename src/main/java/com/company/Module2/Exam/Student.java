package com.company.Module2.Exam;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Student implements Serializable {
    private int id;
    private String firstName;
    private String secondName;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private Faculty faculty;
    private String course;
    private String group;

    public Student(int id, String firstName, String secondName, LocalDate birthDate, String address, String phone, Faculty faculty, String course, String group) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.faculty = faculty;
        this.course = course;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public String getCourse() {
        return course;
    }

    public String getGroup() {
        return group;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public enum Faculty{
        FKNK,
        FIZ,
        CHEM
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Id: ");
        sb.append(id).append(", First name: ").append(firstName);
        sb.append(", Second name: ").append(secondName);
        sb.append(", Date of birth: ").append(birthDate);
        sb.append(", Address: ").append(address).append(", Phone: ");
        sb.append(phone).append(", Faculty: ").append(faculty);
        sb.append(", Course: ").append(course);
        sb.append(". Group: ").append(group);
        return sb.toString();
    }
}
