package com.company.Module2.Lab1;

import java.util.ArrayList;
import java.util.Random;

public class DepartmentUnit {
    private int id;
    private String name;
    private int numberOfEmloyees;
    private ArrayList<Employee> employees;

    public DepartmentUnit(int id, String name) {
        this.id = id;
        this.name = name;
        employees = new ArrayList<>();
    }

    public DepartmentUnit() {
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfEmloyees() {
        return numberOfEmloyees;
    }

    public void setCode(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        numberOfEmloyees++;
    }

    public Employee getEmployee(String name) {
        for (Employee person : employees) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }

    public void fixHeadsOfDepartmentNumber(Employee newEmployee) {
        if (!newEmployee.isDepartmentHead()) {
            return;
        }
        for (Employee employee : employees) {
            if (employee.isDepartmentHead()) {
                newEmployee.setDepartmentHead(false);
                break;
            }
        }
    }

    public void fixHeadsOfDepartment() {
        if (numberOfEmloyees == 0) {
            return;
        }

        Random random = new Random();
        for (Employee employee : employees) {
            if (employee.isDepartmentHead()) {
                return;
            }
        }

        int index = random.nextInt(numberOfEmloyees);
        employees.get(index).setDepartmentHead(true);
    }

    public int countEmployees() {
        return employees.size();
    }

    public Employee getEmployee(int id) {
        for (Employee person : employees) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    public void deleteEmployee(String name) {
        Employee toDelete = getEmployee(name);

        if (toDelete != null) {
            employees.remove(toDelete);
            numberOfEmloyees--;
        } else {
            System.out.printf("There is no Employee with name %s in Department unit %s\n", name, this.name);
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("\n\tDepartmentUnit\n");
        sb.append("\tid=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", numberOfEmloyees= '").append(numberOfEmloyees).append('\'');
        sb.append(", employees:\n").append(employees).append("\n");
        return sb.toString();
    }
}
