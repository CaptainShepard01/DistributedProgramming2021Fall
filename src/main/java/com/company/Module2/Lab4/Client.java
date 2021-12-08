package com.company.Module2.Lab4;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws
            MalformedURLException, NotBoundException, RemoteException {
        StaffDepartment s = (StaffDepartment) Naming.lookup("//127.0.0.1/StaffDepartment");

        String unitName = "Unit-0";
        System.out.printf("Adding new unit with name %s: %b", unitName, s.addNewUnit(unitName));

        unitName = "Unit-1";
        System.out.printf("Adding new unit with name %s: %b", unitName, s.addNewUnit(unitName));

        unitName = "Unit-2";
        System.out.printf("Adding new unit with name %s: %b", unitName, s.addNewUnit(unitName));

        System.out.printf("Adding employee Name: %s; DeptHead: %b; Employment: %d; UnitName: %s:\n%b\n\n", "Vasya", true, 2, "Unit-0", s.addEmployeeInUnit("Vasya", true, 2, "Unit-0"));
        System.out.printf("Adding employee Name: %s; DeptHead: %b; Employment: %d; UnitName: %s:\n%b\n\n", "Vova", false, 3, "Unit-0", s.addEmployeeInUnit("Vova", false, 3, "Unit-0"));
        System.out.printf("Adding employee Name: %s; DeptHead: %b; Employment: %d; UnitName: %s:\n%b\n\n", "Petya", false, 4, "Unit-0", s.addEmployeeInUnit("Petya", false, 4, "Unit-0"));
        System.out.printf("Adding employee Name: %s; DeptHead: %b; Employment: %d; UnitName: %s:\n%b\n\n", "Semen", true, 5, "Unit-1", s.addEmployeeInUnit("Semen", true, 5, "Unit-1"));
        System.out.printf("Adding employee Name: %s; DeptHead: %b; Employment: %d; UnitName: %s:\n%b\n\n", "Gosha", false, 6, "Unit-1", s.addEmployeeInUnit("Gosha", false, 6, "Unit-1"));
        System.out.printf("Adding employee Name: %s; DeptHead: %b; Employment: %d; UnitName: %s:\n%b\n\n", "Nazar", true, 7, "Unit-2", s.addEmployeeInUnit("Nazar", true, 7, "Unit-2"));

        System.out.printf("Adding employee Name: %s; DeptHead: %b; Employment: %d; UnitName: %s:\n%b\n\n", "Illya", false, 8, "Unit-5", s.addEmployeeInUnit("Illya", false, 8, "Unit-5"));

        System.out.printf("Changing employee's name Name: %s; UnitUname: %s; NewName: %s:\n%b\n\n", "Petya", "Unit-0", "Fedor", s.changeEmployeeName("Petya", "Unit-0", "Fedor"));
        System.out.printf("Changing employee's name Name: %s; UnitUname: %s; NewName: %s:\n%b\n\n", "Petya", "Unit-0", "Pasha", s.changeEmployeeName("Petya", "Unit-0", "Pasha"));

        System.out.printf("Changing employee's name Name: %s; UnitUname: %s; NewUnitName: %s:\n%b\n\n", "Vova", "Unit-0", "Unit-3", s.changeEmployeeUnit("Vova", "Unit-0", "Unit-3"));

        System.out.printf("Count of employees in unit %s: %d\n\n", "Unit-2", s.countEmployeesInUnit("Unit-2"));
        System.out.printf("Count of employees in unit %s: %d\n\n", "Unit-1", s.countEmployeesInUnit("Unit-1"));

        System.out.printf("Employees from unit %s:\n%s\n\n", "Unit-1", s.getEmployeesFromUnit("Unit-1"));

        System.out.printf("List list of units:\n%s\n\n", s.getUnitsList());
    }
}
