package com.company.Module2.Lab2;

import java.sql.SQLException;
import java.util.List;

public interface DAOInterface {
    public void stop() throws SQLException;

    public void showDepartmentUnits();
    public void showEmployees();

    public String addNewUnit(String[] params);
    public String deleteUnit(String[] params);
    public String addEmployeeInUnit(String[] params);
    public String deleteEmployeeFromUnit(String[] params);
    public String changeEmployeeName(String[] params);
    public String changeEmployeeUnit(String[] params);
    public String countEmployeesInUnit(String[] params);
    public String getEmployeesFromUnit(String[] params);
    public String getUnitsList(String[] params);

    public List<DepartmentUnit> getDepartmentUnits();
    public boolean addDepartmentUnit(String name);
    public DepartmentUnit findDepartmentUnit(int id);
    public DepartmentUnit findDepartmentUnit(String name);

    public List<DepartmentUnit> findDepartmentUnits(int numberOfEmployees);
    public void deleteAllUnits();

    public void deleteAllEmployees();
    public boolean deleteDepartmentUnit(int id);
    public boolean deleteDepartmentUnit(String name);

    public boolean setDepartmentUnit(DepartmentUnit departmentUnit);
    public boolean addEmployee(String name, boolean isDepartmentHead, int lengthOfEmployment, String unitName);

    public boolean deleteEmployee(int id);
    public boolean deleteEmployee(String name, String unitName);

    public boolean setEmployee(String name, String unitName, String newName, boolean isNewUnitName);
    public boolean setEmployee(String name, String unitName, int lengthOfEmployment);

    public Employee findEmployee(String name, String unitName);
    public List<Employee> findDepartmentHeads(boolean isDepartmentHead);
    public List<Employee> findEmployees(String name);
    public List<Employee> findEmployees(int lengthOfEmployment);
    public List<Employee> findEmployeesFromUnit(String unitName);

    public int countEmployees(String unitName);
}
