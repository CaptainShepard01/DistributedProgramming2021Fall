package com.company.Module2.Lab1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Manager {
    public StaffDepartment readFromFile() {
        DocumentBuilder db = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        dbf.setValidating(true);
        db.setErrorHandler(new SimpleErrorHandler());

        Document doc = null;
        StaffDepartment department = new StaffDepartment();
        try {
            doc = db.parse(new File("src/main/java/com/company/Module2/Lab1/StaffDepartment.xml"));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
            return department;
        }

        Element root = doc.getDocumentElement();
        if (root.getTagName().equals("StaffDepartment")) {
            NodeList listDepartmentUnits = root.getElementsByTagName("DepartmentUnit");

            for (int i = 0; i < listDepartmentUnits.getLength(); i++) {
                Element departmentUnit = (Element) listDepartmentUnits.item(i);
                String departmentUnitCode = departmentUnit.getAttribute("id");
                String departmentUnitName = departmentUnit.getAttribute("name");

                DepartmentUnit unit = new DepartmentUnit(Integer.parseInt(departmentUnitCode),
                        departmentUnitName);

                department.addDepartmentUnit(unit);

                NodeList listEmployees = departmentUnit.getElementsByTagName("Employee");

                for (int j = 0; j < listEmployees.getLength(); j++) {

                    Element employee = (Element) listEmployees.item(j);
                    String employeeId = employee.getAttribute("id");
                    String employeeName = employee.getAttribute("name");
                    String employeeIsDepartmentHead = employee.getAttribute("isDepartmentHead");
                    String employeeLengthOfEmployment = employee.getAttribute("lengthOfEmployment");

                    Employee employee1 = new Employee(Integer.parseInt(employeeId),
                            employeeName,
                            employeeIsDepartmentHead.equals("Yes"),
                            Integer.parseInt(employeeLengthOfEmployment));

                    unit.addEmployee(employee1);
                }
            }
        }
        System.out.println(department);
        return department;
    }

    private void createDOMFromClass(StaffDepartment department, Document doc) {
        Element root = doc.createElement("StaffDepartment");
        doc.appendChild(root);

        ArrayList<DepartmentUnit> unitLists = department.getUnits();

        for (DepartmentUnit unit : unitLists) {
            Element deptUnit = doc.createElement("DepartmentUnit");

            deptUnit.setAttribute("id", String.valueOf(unit.getId()));
            deptUnit.setAttribute("name", unit.getName());
            deptUnit.setAttribute("numberOfEmloyees", String.valueOf(unit.getNumberOfEmloyees()));

            root.appendChild(deptUnit);

            ArrayList<Employee> employees = unit.getEmployees();

            for (Employee employee : employees) {
                Element employeeElement = doc.createElement("Employee");

                employeeElement.setAttribute("id", String.valueOf(employee.getId()));
                employeeElement.setAttribute("name", employee.getName());
                employeeElement.setAttribute("isDepartmentHead", String.valueOf(employee.isDepartmentHead()));
                employeeElement.setAttribute("lengthOfEmployment", String.valueOf(employee.getLengthOfEmployment()));

                deptUnit.appendChild(employeeElement);
            }
        }
    }

    public void writeToFile(StaffDepartment department) throws TransformerException {
        DocumentBuilder db = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        dbf.setValidating(true);

        Document doc = db.newDocument();

        createDOMFromClass(department, doc);

        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(new File("src/main/java/com/company/Module2/Lab1/StaffDepartment.xml"));
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(domSource, fileResult);
    }

    public void deleteDepartmentUnit(String unitName) throws TransformerException {
        StaffDepartment department = readFromFile();

        department.deleteDepartmentUnit(unitName);

        writeToFile(department);
    }

    public void addDepartmentUnit(int id, String unitName, int numberOfEmployees) throws TransformerException {
        DepartmentUnit unit = new DepartmentUnit(id, unitName);

        StaffDepartment department = readFromFile();

        department.addDepartmentUnit(unit);

        writeToFile(department);
    }

    public void deleteEmployee(String unitName, String name) throws TransformerException {
        StaffDepartment department = readFromFile();

        DepartmentUnit unit = department.getDepartmentUnit(unitName);

        unit.deleteEmployee(name);

        writeToFile(department);
    }

    public enum fields {
        NAME,
        LENGTHOFEMPLOYMENT
    }

    public void updateEmployee(String unitName, String name, fields field) throws IOException {
        StaffDepartment department = readFromFile();

        DepartmentUnit unit = department.getDepartmentUnit(unitName);

        if (unit != null) {
            Employee employee = unit.getEmployee(name);
            if (employee != null) {
                InputStreamReader reader = new InputStreamReader(System.in);
                BufferedReader in = new BufferedReader(reader);
                switch (field) {
                    case NAME -> {
                        System.out.printf("Enter new name for Employee %s :", employee.getName());
                        String newName = in.readLine();

                        if (!newName.equals(employee.getName())) {
                            employee.setName(newName);
                        } else {
                            System.out.printf("You have entered the same name for Employee %s\n", employee.getName());
                        }
                    }
                    case LENGTHOFEMPLOYMENT -> {
                        System.out.printf("Enter new length of employment for Employee %s :", employee.getName());
                        int newLengthOfEmployment = Integer.parseInt(in.readLine());

                        if (newLengthOfEmployment != employee.getLengthOfEmployment()) {
                            employee.setLengthOfEmployment(newLengthOfEmployment);
                        } else {
                            System.out.printf("You have entered the same length of employment for Employee %s : %d\n",
                                    employee.getName(), employee.getLengthOfEmployment());
                        }
                    }
                }
            } else {
                System.out.printf("There is no such employee %s in Department unit %s\n", name, unitName);
            }
        } else {
            System.out.printf("There is no such Department unit %s\n", unitName);
        }
    }

    public void dummyInitialization() throws TransformerException {
        StaffDepartment department = StaffDepartment.dummyInitialization(5);
        writeToFile(department);
    }
}
