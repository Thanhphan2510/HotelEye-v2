package com.example.hoteleye.databases.entities;

import java.util.List;

public class Reservation {
    private int reservation_id;
    private Employee employee;
    private Room room;
    private Customer customer;
    private String reservation_date;
    private String arrival_date;        //check in
    private String departure_date;      //check out
    private float deposit;
    private float discount;
    private float total_price;
    private List<SurCharge> surCharges;
    private int is_checkedIn;
    private String note;

}
