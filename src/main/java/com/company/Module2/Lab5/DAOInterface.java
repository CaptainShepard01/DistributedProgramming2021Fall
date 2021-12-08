package com.company.Module2.Lab5;

import java.sql.SQLException;
import java.util.List;

public interface DAOInterface {
    void stop() throws SQLException;

    void showDepartmentUnits();
    void showEmployees();

    String addNewUnit(String[] params);
    String deleteUnit(String[] params);
    String addEmployeeInUnit(String[] params);
    String deleteEmployeeFromUnit(String[] params);
    String changeEmployeeName(String[] params);
    String changeEmployeeUnit(String[] params);
    String countEmployeesInUnit(String[] params);
    String getEmployeesFromUnit(String[] params);
    String getUnitsList(String[] params);

    List<DepartmentUnit> getDepartmentUnits();
    boolean addDepartmentUnit(String name);
    DepartmentUnit findDepartmentUnit(int id);
    DepartmentUnit findDepartmentUnit(String name);

    List<DepartmentUnit> findDepartmentUnits(int numberOfEmployees);
    void deleteAllUnits();

    void deleteAllEmployees();
    boolean deleteDepartmentUnit(int id);
    boolean deleteDepartmentUnit(String name);

    boolean setDepartmentUnit(DepartmentUnit departmentUnit);
    boolean addEmployee(String name, boolean isDepartmentHead, int lengthOfEmployment, String unitName);

    boolean deleteEmployee(int id);
    boolean deleteEmployee(String name, String unitName);

    boolean setEmployee(String name, String unitName, String newName, boolean isNewUnitName);
    boolean setEmployee(String name, String unitName, int lengthOfEmployment);

    Employee findEmployee(String name, String unitName);
    List<Employee> findDepartmentHeads(boolean isDepartmentHead);
    List<Employee> findEmployees(String name);
    List<Employee> findEmployees(int lengthOfEmployment);
    List<Employee> findEmployeesFromUnit(String unitName);

    int countEmployees(String unitName);
}
