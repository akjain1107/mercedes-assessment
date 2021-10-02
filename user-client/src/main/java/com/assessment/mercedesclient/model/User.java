package com.assessment.mercedesclient.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class User {
    private String uuid;
    private String name;
    private String dob;
    private double salary;
    private int age;
    public User(){

    }
    public User(String id, String name, String dob, double salary, int age) {
        this.uuid = id;
        this.name = name;
        this.dob = dob;
        this.salary = salary;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }
    @XmlAttribute
    public void setDob(String dob) {
        this.dob = dob;
    }

    public double getSalary() {
        return salary;
    }
    @XmlAttribute
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }
    @XmlAttribute
    public void setAge(int age) {
        this.age = age;
    }

    public String getUuid() {
        return uuid;
    }
    @XmlAttribute
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                '}';
    }
}
