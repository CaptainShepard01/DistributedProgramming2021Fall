package com.company.Module2.Lab4;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StaffDepartment extends Remote
{
    boolean addNewUnit(String unitName) throws RemoteException;
    boolean deleteUnit(String unitName) throws RemoteException;
    boolean addEmployeeInUnit(String name, boolean isUnitHead, int lengthOfEmployment, String unitName) throws RemoteException;
    boolean deleteEmployeeFromUnit(String employeeName, String unitName) throws RemoteException;
    boolean changeEmployeeName(String employeeName, String unitName, String newName) throws RemoteException;
    boolean changeEmployeeUnit(String employeeName, String unitName, String newName) throws RemoteException;
    int countEmployeesInUnit(String unitName) throws RemoteException;
    List<Employee> getEmployeesFromUnit(String unitName) throws RemoteException;
    List<DepartmentUnit> getUnitsList() throws RemoteException;
}
