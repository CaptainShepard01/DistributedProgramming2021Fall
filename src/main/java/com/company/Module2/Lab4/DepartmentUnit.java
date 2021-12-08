package com.company.Module2.Lab4;

import java.io.Serializable;

public class DepartmentUnit implements Serializable {
    private int id;
    private String name;

    public DepartmentUnit(String name) {
        this.name = name;
    }

    public DepartmentUnit(int id, String name, int numberOfEmloyees) {
        this.id = id;
        this.name = name;
    }

    public DepartmentUnit(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public DepartmentUnit() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setCode(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("\nDepartmentUnit: ");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        return sb.toString();
    }
}
