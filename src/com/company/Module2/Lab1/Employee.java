package com.company.Module2.Lab1;

public class Employee {
    private int id;
    private String name;
    private boolean isDepartmentHead;
    private int lengthOfEmployment;

    public Employee(int id, String name, boolean isDepartmentHead, int lengthOfEmployment) {
        this.id = id;
        this.name = name;
        this.isDepartmentHead = isDepartmentHead;
        this.lengthOfEmployment = lengthOfEmployment;
    }

    public Employee(){
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDepartmentHead() {
        return isDepartmentHead;
    }

    public int getLengthOfEmployment() {
        return lengthOfEmployment;
    }

    public void setCode(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartmentHead(boolean departmentHead) {
        isDepartmentHead = departmentHead;
    }

    public void setLengthOfEmployment(int lengthOfEmployment) {
        this.lengthOfEmployment = lengthOfEmployment;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("\n\t\tEmployee\n");
        sb.append("\t\tid=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", isDepartmentHead='").append(isDepartmentHead?"Yes":"No").append('\'');
        sb.append(", lengthOfEmployment='").append(lengthOfEmployment).append("\'\n");
        return sb.toString();
    }
}
