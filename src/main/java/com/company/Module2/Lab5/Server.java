package com.company.Module2.Lab5;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.jms.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class Server {
    private Connection connection;

    DAOInterface departmentDAO;

    public enum Query {
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

    public void start() throws IOException, TimeoutException, JMSException, SQLException, ClassNotFoundException {
        departmentDAO = new StaffDepartmentDAO();
        departmentDAO.deleteAllEmployees();
        departmentDAO.deleteAllUnits();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();

        Channel channelFromClient = connection.createChannel();
        Channel channelToClient = connection.createChannel();
        channelFromClient.queueDeclare("fromClient", false, false, false, null);
        channelToClient.queueDeclare("toClient", false, false, false, null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String query = new String(delivery.getBody(), StandardCharsets.UTF_8);

            System.out.println(" [x] Received '" + query + "'\n");
            String response = processQuery(query);

            channelToClient.basicPublish("", "toClient", null, response.getBytes(StandardCharsets.UTF_8));
        };
        channelFromClient.basicConsume("fromClient", true, deliverCallback, consumerTag -> { });
    }

    private String processQuery(String query) {
        int compCode = 0;
        String response = "";
        try {
            String[] fields = query.split("#");
            if (fields.length == 0) {
                return response;
            } else {
                Query queryType = Query.valueOf(fields[0]);
                DepartmentUnit departmentUnit;
                Employee employee;

//                System.out.println(queryType);
                response = compCode + "#";
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
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return response;
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, JMSException, IOException, ClassNotFoundException, TimeoutException {
        var server = new Server();
        server.start();
    }
}
