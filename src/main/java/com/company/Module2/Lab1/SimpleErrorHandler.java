package com.company.Module2.Lab1;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleErrorHandler implements ErrorHandler {
    @Override
    public void warning(SAXParseException e) throws SAXException {
        System.out.println("Line " + e.getLineNumber() + ":");
        System.out.println(e.getMessage());
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        System.out.println("Line " + e.getLineNumber() + ":");
        System.out.println(e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        System.out.println("Line " + e.getLineNumber() + ":");
        System.out.println(e.getMessage());
    }
}