package com.company.Module2.Lab2;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        StaffDepartmentDAO departmentDAO = new StaffDepartmentDAO();

        departmentDAO.deleteAllEmployees();
        departmentDAO.deleteAllUnits();

        departmentDAO.addDepartmentUnit("Unit-1");
        departmentDAO.addDepartmentUnit("Unit-2");
        departmentDAO.addDepartmentUnit("Unit-3");

        departmentDAO.addEmployee("Vasya", true, 1, "Unit-1");
        departmentDAO.addEmployee("Vova", false, 2, "Unit-1");

        departmentDAO.addEmployee("Sanya", true,25,  "Unit-2");
        departmentDAO.addEmployee("Egor", false, 30, "Unit-2");
        departmentDAO.addEmployee("Eugene", false, 18, "Unit-2");

        departmentDAO.addEmployee("Nazar", true,22, "Unit-3");
        departmentDAO.addEmployee("Anton", false,33, "Unit-3");
        departmentDAO.addEmployee("Ruslan", false,67, "Unit-3");

        departmentDAO.addEmployee("Andriy", true,5, "Unit-5");

        departmentDAO.deleteEmployee("Egor", "Unit-2");
        departmentDAO.deleteEmployee("Gosha", "Unit-4");

        departmentDAO.deleteDepartmentUnit("Unit-1");

        departmentDAO.setEmployee("Anton", "Unit-3", "Fedor");
        departmentDAO.setEmployee("Anton", "Unit-3", "Egor");

        System.out.println(departmentDAO.findEmployee("Ruslan", "Unit-3"));

        System.out.println();
        departmentDAO.showDepartmentUnits();

        System.out.println();
        departmentDAO.showEmployees();

        departmentDAO.stop();
    }
}
