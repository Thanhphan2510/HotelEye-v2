package com.example.hoteleye.viewmodels;

import java.io.Serializable;

public class ReservationOverviewItem implements Serializable {
    private String reservation_id;
    private int room_name;
    private String reservation_date,arrival_date, departure_date;
    private String customer_name;
    private float deposit;
    private String note;

    public ReservationOverviewItem() {
    }

    public String getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(String reservation_id) {
        this.reservation_id = reservation_id;
    }

    public ReservationOverviewItem(int room_name, String reservation_date, String arrival_date, String departure_date, String customer_name, float deposit, String note) {
        this.room_name = room_name;
        this.reservation_date = reservation_date;
        this.arrival_date = arrival_date;
        this.departure_date = departure_date;
        this.customer_name = customer_name;
        this.deposit = deposit;
        this.note = note;
    }

    public String getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(String arrival_date) {
        this.arrival_date = arrival_date;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }


    public int getRoom_name() {
        return room_name;
    }

    public void setRoom_name(int room_name) {
        this.room_name = room_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
