package com.assessment.mercedesservice.handler;

import com.assessment.mercedesservice.model.User;
import com.assessment.mercedesservice.service.XMLFileCreator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XMLHandler {

    public boolean addUser(String xmlFilePath, User request)
            throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File xmlFile = XMLFileCreator.getInstance(xmlFilePath);
        Document document = getWriteDocument(xmlFile);
        Element root = getRoot(document, xmlFile);
        root.appendChild(createXMLTags(document, request));
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(xmlFile);
        TransformerFactory.
                newInstance().newTransformer()
                .transform(source, result);
        return true;
    }

    public boolean updateUser(String filePath, com.assessment.mercedesbenz.gprc.User.UpdateDetails request)
            throws IOException, ParserConfigurationException, SAXException {
            File xmlFile = XMLFileCreator.getInstance(filePath);
        if(isEmpty(xmlFile)){
            return false;
        }
        Document document = getDocument(xmlFile);
        return updateElementValue(document, request,filePath, getNodeElement(document));
    }

    private boolean isEmpty(File xmlFile) {
        if (xmlFile.length() == 0) {
            System.out.println("Root added to empty file!!");
            return true;
        }
        return false;
    }

    private Element getRoot(Document document, File xmlFile) {
        Element root;
        if (xmlFile.length() == 0) {
            System.out.println("Root added to empty file!!");
            root = document.createElement("users");
            document.appendChild(root);
        } else {
            System.out.println("Root added into non empty file!!");
            root = document.getDocumentElement();
        }
        return root;
    }


    private NodeList getNodeElement(Document document) {
        Element root = document.getDocumentElement();
        return root.getElementsByTagName("user");
    }

    private Document getDocument(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = getDocumentBuilder();
        Document document = builder.parse(xmlFile);
        document.normalizeDocument();
        return document;
    }

    private boolean updateElementValue(Document document,
                                       com.assessment.mercedesbenz.gprc.User.UpdateDetails request,
                                       String filePath, NodeList nodeElement) {

        for (int i = 0; i < nodeElement.getLength(); i++) {
            Element user = (Element) nodeElement.item(i);
            Node id = user.getElementsByTagName("id").item(0).getFirstChild();
            if (id.getNodeValue().equalsIgnoreCase(request.getFilterId())) {
                com.assessment.mercedesbenz.gprc.User.UserDetails userDetails = request.getUserDetails();
                user.getElementsByTagName("name").item(0).getFirstChild().setNodeValue(userDetails.getName());
                user.getElementsByTagName("dob").item(0).getFirstChild().setNodeValue(userDetails.getDob());
                user.getElementsByTagName("salary").item(0).getFirstChild().setNodeValue(userDetails.getSalary());
                user.getElementsByTagName("age").item(0).getFirstChild().setNodeValue(String.valueOf(userDetails.getAge()));
                return updateDocument(document,filePath);
            }
        }
        return false;
    }

    private boolean updateDocument(Document document,String xmlFilePath) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));
            transformer.transform(domSource, streamResult);
            return true;
        } catch (TransformerException e) {
            return false;
        }
    }
    private Document getWriteDocument(File xmlFile) throws
            ParserConfigurationException, IOException, SAXException {
        return setUpDocumentBuider(xmlFile);
    }

    private Document setUpDocumentBuider(File xmlFile) throws
            ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = getDocumentBuilder();
        Document document;
        if (xmlFile.length() == 0) {
            System.out.println("File is empty");
            document = documentBuilder.newDocument();
        } else {
            System.out.println("File is not empty");
            document = documentBuilder.parse(xmlFile);
        }
        return document;
    }

    private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    private Element createXMLTags(Document document, User request) {

        Element user = document.createElement("user");
        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(request.getUuid()));
        user.appendChild(id);
        Element name = document.createElement("name");
        name.appendChild(document.createTextNode(request.getName()));
        user.appendChild(name);
        Element dob = document.createElement("dob");
        dob.appendChild(document.createTextNode(request.getDob()));
        user.appendChild(dob);
        Element salary = document.createElement("salary");
        salary.appendChild(document.createTextNode(String.valueOf(request.getSalary())));
        user.appendChild(salary);
        Element age = document.createElement("age");
        age.appendChild(document.createTextNode(String.valueOf(request.getAge())));
        user.appendChild(age);
        return user;
    }

    public List<User> readUser(String xmlFilePath, String request) throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = XMLFileCreator.getInstance(xmlFilePath);
        if(isEmpty(xmlFile)){
            return new ArrayList<>();
        }
        Document document = getDocument(xmlFile);
        List<User> childNodeList = getChildNodeList(document);
        if(0<childNodeList.size()){
            if ("All".equalsIgnoreCase(request))
                return childNodeList;
            return childNodeList.stream().filter(x -> x.getUuid().equalsIgnoreCase(request))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    private List<com.assessment.mercedesservice.model.User> getChildNodeList(Document document) {
        NodeList nodeElement = getNodeElement(document);
        List<com.assessment.mercedesservice.model.User> userList = new ArrayList<>();
        for (int i = 0; i < nodeElement.getLength(); i++) {
            Node elemNode = nodeElement.item(i);
            if (elemNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) elemNode;
                userList.add(new com.assessment.mercedesservice.model.User(eElement.getElementsByTagName("id").item(0).getTextContent(),
                        eElement.getElementsByTagName("name").item(0).getTextContent(),
                        eElement.getElementsByTagName("dob").item(0).getTextContent(),
                        Double.parseDouble(eElement.getElementsByTagName("salary").item(0).getTextContent()),
                        Integer.parseInt(eElement.getElementsByTagName("age").item(0).getTextContent())));
            }
        }
        return userList;
    }
}
