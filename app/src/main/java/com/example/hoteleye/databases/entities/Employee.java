package com.example.hoteleye.databases.entities;

import java.io.Serializable;

public class Employee extends Person implements Serializable {
    private String employee_id;
    private Account account;
    private String position;
    private Salary salary;
    private String personID;

    public Employee(String employee_id) {
        this.employee_id = employee_id;
    }

    public Employee(String personID, String employee_id) {
        super(personID);
        this.employee_id = employee_id;
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public Employee() {
    }
}
