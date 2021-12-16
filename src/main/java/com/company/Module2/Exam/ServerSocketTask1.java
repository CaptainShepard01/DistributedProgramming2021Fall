package com.company.Module2.Exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Date;

public class ServerSocketTask1 {
    private Socket socket = null;
    private ServerSocket server = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private static StudentDao studentDao;

    public static enum Query {
        Students_of_faculty,
        Students_birth_after_date,
        Students_from_group,
        Students_from_all_faculties
    }

    public void start(int port) {
        try {
            studentDao = new StudentDao();
            studentDao.addStudent(new Student(1, "Anton", "Balykov", LocalDate.parse("2001-05-05"), "Address", "4546778", Student.Faculty.FKNK, "3", "EPS-31"));
            studentDao.addStudent(new Student(2, "John", "Smith", LocalDate.parse("2005-05-05"), "Address1", "4546778678", Student.Faculty.FKNK, "2", "EPS-32"));
            studentDao.addStudent(new Student(3, "Frank", "Jakarta", LocalDate.parse("1999-05-05"), "Address2", "5686579675", Student.Faculty.FIZ, "3", "FIZ-1"));
            studentDao.addStudent(new Student(3, "Frank", "Jakarta", LocalDate.parse("2002-05-05"), "Address2", "5686579675", Student.Faculty.FIZ, "3", "FIZ-2"));

            server = new ServerSocket(port);
            int i = 0;
            while (i++ < 5) {
                System.out.println("Waiting for a client ...");
                new Thread(()->{
                    try {
                        socket = server.accept();
                        System.out.println("Client connected");

                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out = new PrintWriter(socket.getOutputStream(), true);

                        while (processQuery()) ;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            System.out.println("Error >>     " + e.getMessage());
        }
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
                case Students_from_group -> {
                    String group = fields[1];

                    response += studentDao.studentsFromGroup(group);
                }
                case Students_birth_after_date -> {
                    LocalDate date = LocalDate.parse(fields[1]);

                    response += studentDao.studentsBirthAfterDate(date);
                }
                case Students_of_faculty -> {
                    Student.Faculty faculty = Student.Faculty.valueOf(fields[1]);

                    response += studentDao.studentsFromFaculty(faculty);
                }
                case Students_from_all_faculties -> {
                    response += studentDao.studentsFromAllFaculties();
                }
            }

            out.println(response);
            return true;
        } catch (IOException e) {
            System.out.println("Error >>     " + e.getMessage());
            out.println(-1);
            return false;
        }
    }

    public static void main(String[] args) {
        ServerSocketTask1 srv = new ServerSocketTask1();
        srv.start(12345);
    }
}
