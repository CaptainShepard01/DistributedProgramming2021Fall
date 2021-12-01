package com.company.Module2.Lab2;

import java.util.ArrayList;
import java.util.Random;

public class StaffDepartment {
    private ArrayList<DepartmentUnit> units;

    public StaffDepartment() {
        units = new ArrayList<>();
    }

    public DepartmentUnit getDepartmentUnit(String name) {
        for (DepartmentUnit unit : units) {
            if (unit.getName().equals(name)) {
                return unit;
            }
        }
        return null;
    }

    public void deleteDepartmentUnit(String name) {
        DepartmentUnit unitToDelete = getDepartmentUnit(name);
        deleteDepartmentUnit(unitToDelete);
    }

    public ArrayList<DepartmentUnit> getUnits() {
        return units;
    }

    public void addDepartmentUnit(DepartmentUnit unit) {
        units.add(unit);
    }

    public int countDepartmentUnits() {
        return units.size();
    }

    private void deleteDepartmentUnit(DepartmentUnit unit) {
        DepartmentUnit toDelete = getDepartmentUnit(unit.getName());

        if (toDelete != null) {
            deleteDepartmentUnit(unit);
        } else {
            System.out.printf("There is no such Department unit: %s\n", unit.getName());
        }
    }

    public static StaffDepartment dummyInitialization(int maxUnits) {
        StaffDepartment newDepartment = new StaffDepartment();
        Random random = new Random();
        int numUnits = random.nextInt(maxUnits - 1) + 1;;
        for (int i = 0; i < numUnits; ++i) {
            DepartmentUnit newUnit = new DepartmentUnit(i, "Unit-" + i);
            newDepartment.addDepartmentUnit(newUnit);
            int maxEmployees = random.nextInt(4);
            for (int j = 0; j < maxEmployees; ++j) {
                Employee employee = new Employee(j, "Employee-" + j, random.nextBoolean(), random.nextInt(50));
                newUnit.fixHeadsOfDepartmentNumber(employee);
                newUnit.addEmployee(employee);
            }
            newUnit.fixHeadsOfDepartment();
        }

        return newDepartment;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StaffDepartment\n");
        sb.append("Department units:\n").append(units).append("\n");
        return sb.toString();
    }
}
