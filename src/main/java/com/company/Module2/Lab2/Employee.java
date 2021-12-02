package com.company.Module2.Lab2;

import java.io.Serializable;

public class Employee implements Serializable {
    private int id;
    private String name;
    private boolean isDepartmentHead;
    private int lengthOfEmployment;
    private int unitId;

    public Employee(String name, boolean isDepartmentHead, int lengthOfEmployment, int unitId){
        this.name = name;
        this.isDepartmentHead = isDepartmentHead;
        this.lengthOfEmployment = lengthOfEmployment;
        this.unitId = unitId;
    }

    public Employee(int id, String name, boolean isDepartmentHead, int lengthOfEmployment, int unitId) {
        this.id = id;
        this.name = name;
        this.isDepartmentHead = isDepartmentHead;
        this.lengthOfEmployment = lengthOfEmployment;
        this.unitId = unitId;
    }

    public Employee(int id, String name, boolean isDepartmentHead, int lengthOfEmployment) {
        this.id = id;
        this.name = name;
        this.isDepartmentHead = isDepartmentHead;
        this.lengthOfEmployment = lengthOfEmployment;
        this.unitId = -1;
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

    public int getUnitId() {
        return unitId;
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

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Employee: ");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", isDepartmentHead='").append(isDepartmentHead?"Yes":"No").append('\'');
        sb.append(", lengthOfEmployment='").append(lengthOfEmployment).append('\'');
        sb.append(", unitId='").append(unitId).append('\'');
        return sb.toString();
    }
}
