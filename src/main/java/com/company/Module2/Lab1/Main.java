package com.company.Module2.Lab1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws TransformerException {
        Manager manager = new Manager();
//        StaffDepartment department = manager.readFromFile();
//        manager.writeToFile(department);
        manager.dummyInitialization();
        manager.readFromFile();

        manager.deleteDepartmentUnit("Unit-0");
        manager.addDepartmentUnit(8, "Unit-365", 0);
    }
}
