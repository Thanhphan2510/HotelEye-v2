package com.example.hoteleye.databases.entities;

import java.io.Serializable;

public class Person implements Serializable {
    private String personID;
    private int id_card;
    private String fullName;
    private String dateOfBirth;
    private int gender;
    private String address, phone_number, mail, note;

    public Person() {
    }

    public Person(String personID) {
        this.personID = personID;
    }

    public Person(int id_card, String fullName, String dateOfBirth, int gender, String address, String phone_number, String mail, String note) {
        this.id_card = id_card;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phone_number = phone_number;
        this.mail = mail;
        this.note = note;
    }

    public int getId_card() {
        return id_card;
    }

    public void setId_card(int id_card) {
        this.id_card = id_card;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
