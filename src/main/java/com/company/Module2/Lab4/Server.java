package com.company.Module2.Lab4;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws RemoteException{
        try {
            Registry r = LocateRegistry.createRegistry(1099);
            StaffDepartmentDAO server = new StaffDepartmentDAO();
            server.deleteAllEmployees();
            server.deleteAllUnits();
            r.rebind("StaffDepartment", server);
            System.out.println("Server started!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}