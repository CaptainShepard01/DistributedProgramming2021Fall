package com.company.Module2.Exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketTask1 {
    private Socket socket = null;
    private BufferedReader in;
    private PrintWriter out;

    public ClientSocketTask1(String ip, int port) {
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
        ClientSocketTask1 client = new ClientSocketTask1("localhost", 12345);

        client.sendQuery("Students_of_faculty#FKNK");
        client.sendQuery("Students_of_faculty#FIZ");

        client.sendQuery("Students_birth_after_date#2000-05-05");

        client.sendQuery("Students_from_group#EPS-31");

        client.sendQuery("Students_from_all_faculties#");

        client.disconnect();
    }
}
