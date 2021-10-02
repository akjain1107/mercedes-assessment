package com.assessment.mercedesservice.service;

import com.assessment.mercedesbenz.gprc.User;
import com.assessment.mercedesservice.handler.CSVHandler;
import com.assessment.mercedesservice.handler.XMLHandler;
import com.assessment.mercedesservice.model.ReadResponse;
import com.assessment.mercedesservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {
    @Value("${datasource.path.xml}")
    private String xmlFilePath;
    @Value("${datasource.path.csv}")
    private String csvFilePath;

    public User.Response addUser(User.UserDetails request) {
        if (Constants.XML.equalsIgnoreCase(request.getFileType())) {
            try {
                XMLHandler csvHandler = new XMLHandler();
                boolean status = csvHandler.addUser(xmlFilePath, getUser(request));
                if(status)
                    return sendStatus(0, Constants.USER_ADDED_SUCCESSFULLY);
                return sendStatus(1, Constants.SOMETHING_WENT_WRONG);
            } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
                return sendStatus(1, Constants.SOMETHING_WENT_WRONG);
            }
        } else {
            CSVHandler csvHandler = new CSVHandler();
            boolean status = csvHandler.writeUser(csvFilePath, getUser(request));
            if(status)
                return sendStatus(0, Constants.USER_ADDED_SUCCESSFULLY);
            return sendStatus(1, Constants.SOMETHING_WENT_WRONG);
        }
    }

    private com.assessment.mercedesservice.model.User getUser(User.UserDetails request) {
        return new com.assessment.mercedesservice.model.User(request.getId(),
                request.getName(), request.getDob(), Double.parseDouble(request.getSalary()),
                request.getAge());
    }

    public User.Response updateUser(User.UpdateDetails request) {
        if (Constants.XML.equalsIgnoreCase(request.getUserDetails().getFileType())) {
            try {
                XMLHandler xmlHandler = new XMLHandler();
                boolean status = xmlHandler.updateUser(xmlFilePath,request);
                if(status)
                    return sendStatus(0, Constants.USER_UPDATED_SUCCESSFULLY);
                return sendStatus(2, Constants.USER_NOT_FOUND);
            } catch (IOException | ParserConfigurationException | SAXException e) {
                return sendStatus(1, Constants.SOMETHING_WENT_WRONG);
            }
        }else {
            CSVHandler csvHandler = new CSVHandler();
            boolean status = csvHandler.updateUser(csvFilePath,request.getFilterId(),
                    getUser(request.getUserDetails()));
            if(status)
                return sendStatus(0, Constants.USER_UPDATED_SUCCESSFULLY);
            return sendStatus(2, Constants.USER_NOT_FOUND);
        }
    }
    private User.Response sendStatus(int status,String message){
            return User.Response.newBuilder()
                    .setMessage(message)
                    .setStatus(status)
                    .build();
    }

    public ReadResponse read(String request) {
        try {
            List<com.assessment.mercedesservice.model.User> userList =
                    new XMLHandler().readUser(xmlFilePath, request);
            if (0<userList.size()) {
                return new ReadResponse(userList);
            }else {
                return new ReadResponse(new CSVHandler().readUser(csvFilePath, request));
            }
        } catch (Exception ex) {
            return new ReadResponse(new ArrayList<>());
        }
    }
}