package com.company.Module2.Lab5;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.jms.JMSException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Client {
    private Connection connection;

    Channel channelFromClient;
    Channel channelToClient;
    private static final String split = "#";

    public Client() throws IOException, TimeoutException, JMSException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.newConnection();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channelFromClient = connection.createChannel();
        channelToClient = connection.createChannel();
        channelFromClient.queueDeclare("fromClient", false, false, false, null);
        channelToClient.queueDeclare("toClient", false, false, false, null);
    }


    private int sendQuery(String query) {
        try {
            channelFromClient.basicPublish("", "fromClient", null, query.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + query + "'\n");
            Thread.sleep(1000);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String response = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + response + "'\n");

                /*String[] fields = response.split("#");

                try {
                    int compCode = Integer.parseInt(fields[0]);

                    if (compCode == 0) {
                        System.out.println("\nQuery:");
                        for (int i = 1; i < fields.length - 1; ++i) {
                            System.out.println(fields[i]);
                        }
                        System.out.println("Result:");
                        System.out.println(fields[fields.length - 1]);
                    } else {
                        System.out.println("Error while processing query");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid response from server");
                }*/
            };
            channelToClient.basicConsume("toClient", true, deliverCallback, consumerTag -> { });
            return 0;
        } catch (Exception e) {
            System.out.println(">>     " + e.getMessage());
            return -1;
        }


    }

    /*public void cleanMessage() {
        try {
            Message message = consumer.receiveNoWait();

            while (message != null) {
                message = consumer.receiveNoWait();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }*/

    public void close() {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws JMSException, IOException, TimeoutException {
        Client client = new Client();

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

        client.close();
    }
}

