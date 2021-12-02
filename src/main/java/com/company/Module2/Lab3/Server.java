package com.company.Module2.Lab3;

import com.company.Module2.Lab2.DAOInterface;
import com.company.Module2.Lab2.StaffDepartmentDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private static DAOInterface departmentDAO;

    public static enum Query {
        Add_new_unit,
        Delete_unit,
        Add_employee_in_unit,
        Delete_employee_from_unit,
        Change_employee_name,
        Change_employee_unit,
        Count_employees_in_unit,
        Get_employees_from_unit,
        Get_units_list
    }

    public void start(int port) {
        try {
            departmentDAO = new StaffDepartmentDAO();
            departmentDAO.deleteAllEmployees();
            departmentDAO.deleteAllUnits();

            server = new ServerSocket(port);

            while (true) {
                System.out.println("Waiting for a client ...");
                socket = server.accept();
                System.out.println("Client connected");

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while (processQuery()) ;
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            System.out.println("Error >>     " + e.getMessage());
        }
    }


    private Map<Query, String> makeQueriesHashMap(String[] params) {
        Map<Query, String> resultMap = new HashMap<>();

        resultMap.put(Query.Add_new_unit, departmentDAO.addNewUnit(params));
        resultMap.put(Query.Delete_unit, departmentDAO.deleteUnit(params));
        resultMap.put(Query.Add_employee_in_unit, departmentDAO.addEmployeeInUnit(params));
        resultMap.put(Query.Delete_employee_from_unit, departmentDAO.deleteEmployeeFromUnit(params));
        resultMap.put(Query.Change_employee_name, departmentDAO.changeEmployeeName(params));
        resultMap.put(Query.Change_employee_unit, departmentDAO.changeEmployeeUnit(params));
        resultMap.put(Query.Count_employees_in_unit, departmentDAO.countEmployeesInUnit(params));
        resultMap.put(Query.Get_employees_from_unit, departmentDAO.getEmployeesFromUnit(params));

        return resultMap;
    }

    private boolean processQuery() {
        int compCode = 0;
        try {
            String query = in.readLine();

            if (query == null)
                return false;

            String[] fields = query.split("#");

            Query queryType = Query.valueOf(fields[0]);
            String response = compCode + "#";
            switch (queryType) {
                case Add_new_unit -> {
                    String name = fields[1];

                    response += query + "#" + departmentDAO.addDepartmentUnit(name);
                }
                case Delete_unit -> {
                    String name = fields[1];

                    response += query + "#" + departmentDAO.deleteDepartmentUnit(name);
                }
                case Add_employee_in_unit -> {
                    String employeeName = fields[1];
                    boolean isDeptHead = Boolean.parseBoolean(fields[2]);
                    int lengthOfEmployment = Integer.parseInt(fields[3]);
                    String unitName = fields[4];

                    response += query + "#" + departmentDAO.addEmployee(employeeName,
                            isDeptHead,
                            lengthOfEmployment,
                            unitName);
                }
                case Delete_employee_from_unit -> {
                    String name = fields[1];
                    String unitName = fields[2];

                    response += query + "#" + departmentDAO.deleteEmployee(name, unitName);
                }
                case Change_employee_name -> {
                    String name = fields[1];
                    String unitName = fields[2];
                    String newName = fields[3];

                    response += query + "#" + departmentDAO.setEmployee(name, unitName, newName, false);
                }
                case Change_employee_unit -> {
                    String name = fields[1];
                    String unitName = fields[2];
                    String newUnitName = fields[3];

                    response += query + "#" + departmentDAO.setEmployee(name, unitName, newUnitName, true);
                }
                case Count_employees_in_unit -> {
                    String unitName = fields[1];

                    response += query + "#" + departmentDAO.countEmployees(unitName);
                }
                case Get_employees_from_unit -> {
                    String unitName = fields[1];

                    response += query + "#" + departmentDAO.findEmployeesFromUnit(unitName);
                }
                case Get_units_list -> {
                    response += query + "#" + departmentDAO.getDepartmentUnits();
                }
            }

            out.println(response);
            return true;
        } catch (IOException e) {
            System.out.println("Error >>     " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        Server srv = new Server();
        srv.start(12345);
    }
}
