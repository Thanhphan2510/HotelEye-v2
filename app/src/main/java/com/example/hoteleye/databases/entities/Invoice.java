package com.example.hoteleye.databases.entities;

import java.util.ArrayList;

public class Invoice {
    private int id;
    private String payment_date;
    private float payment_amount;
    private String payment_type;
    private Employee employee;
    private Reservation reservation;
    private String note;
    private ArrayList<SurCharge> surCharges;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public float getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(float payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ArrayList<SurCharge> getSurCharges() {
        return surCharges;
    }

    public void setSurCharges(ArrayList<SurCharge> surCharges) {
        this.surCharges = surCharges;
    }

    public Invoice(int id, String payment_date, float payment_amount, String payment_type, Employee employee, Reservation reservation, String note, ArrayList<SurCharge> surCharges) {
        this.id = id;
        this.payment_date = payment_date;
        this.payment_amount = payment_amount;
        this.payment_type = payment_type;
        this.employee = employee;
        this.reservation = reservation;
        this.note = note;
        this.surCharges = surCharges;
    }
}
