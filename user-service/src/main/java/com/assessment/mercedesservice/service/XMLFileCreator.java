package com.assessment.mercedesservice.service;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class XMLFileCreator implements Cloneable {
    private static File xmlFile;
    private XMLFileCreator(){
    }
    public static File getInstance(String xmlFilePath) throws IOException {
        if(xmlFile == null) {
            xmlFile = new File(xmlFilePath);
            xmlFile.createNewFile();
            System.out.println("File created at path: "+xmlFilePath);
        }
        return xmlFile;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }
}
