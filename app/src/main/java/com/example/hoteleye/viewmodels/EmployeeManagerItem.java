package com.example.hoteleye.viewmodels;

public class EmployeeManagerItem {
    private String employeeID;
    private String personID;
    private String accountID;
    private int idCard;
    private String name;
    private String date, phoneNumber, account;

    public EmployeeManagerItem(String employeeID, String personID, String accountID) {
        this.employeeID = employeeID;
        this.personID = personID;
        this.accountID = accountID;
    }

    public EmployeeManagerItem(String employeeID, String personID, String accountID, int idCard, String name, String date, String phoneNumber, String account) {
        this.employeeID = employeeID;
        this.personID = personID;
        this.accountID = accountID;
        this.idCard = idCard;
        this.name = name;
        this.date = date;
        this.phoneNumber = phoneNumber;
        this.account = account;
    }

    public EmployeeManagerItem(int idCard, String name, String date, String phoneNumber, String account) {
        this.idCard = idCard;
        this.name = name;
        this.date = date;
        this.phoneNumber = phoneNumber;
        this.account = account;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
