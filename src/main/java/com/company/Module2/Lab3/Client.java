package com.company.Module2.Lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket = null;
    private BufferedReader in;
    private PrintWriter out;

    public Client(String ip, int port) {
        try {
            System.out.println("Connecting to server...");
            socket = new Socket(ip, port);
            System.out.println("Connected");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error >>     " + e.getMessage());
        }
    }

    private int sendQuery(String query) {
        out.println(query);
        try {
            String response = in.readLine();
            String[] fields = response.split("#");

            if (fields.length == 1) {
                System.out.println("Invalid response from server");
            }

            try {
                int compCode = Integer.parseInt(fields[0]);

                if (compCode == 0) {
                    System.out.println("\n\nQuery:");
                    for (int i = 1; i < fields.length - 1; ++i) {
                        System.out.print(fields[i]);
                        System.out.print("; ");
                    }
                    System.out.println("\nResult:");
                    System.out.println(fields[fields.length - 1]);
                    return 0;
                } else {
                    System.out.println("Error while processing query");
                    return -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid response from server");
                return -1;
            }
        } catch (IOException e) {
            System.out.println(">>     " + e.getMessage());
            return -1;
        }
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error >>     " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 12345);

        client.sendQuery("Add_new_unit#Unit-0");
        client.sendQuery("Add_new_unit#Unit-1");
        client.sendQuery("Add_new_unit#Unit-2");

        client.sendQuery("Add_employee_in_unit#Vasya#true#2#Unit-0");
        client.sendQuery("Add_employee_in_unit#Vova#false#3#Unit-0");
        client.sendQuery("Add_employee_in_unit#Petya#false#4#Unit-0");
        client.sendQuery("Add_employee_in_unit#Semen#true#5#Unit-1");
        client.sendQuery("Add_employee_in_unit#Gosha#false#6#Unit-1");
        client.sendQuery("Add_employee_in_unit#Nazar#true#7#Unit-2");

        client.sendQuery("Add_employee_in_unit#Illya#false#8#Unit-5");

        client.sendQuery("Change_employee_name#Petya#Unit-0#Fedor");
        client.sendQuery("Change_employee_name#Petya#Unit-0#Fedor");

        client.sendQuery("Change_employee_unit#Vova#Unit-0#Unit-3");

        client.sendQuery("Count_employees_in_unit#Unit-2");
        client.sendQuery("Count_employees_in_unit#Unit-1");

        client.sendQuery("Get_employees_from_unit#Unit-1");

        client.sendQuery("Get_units_list");

        client.disconnect();
    }
}
