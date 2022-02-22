package com.example.hoteleye.viewmodels;

public class RoomInvoiceItem {
    private int id;
    private String room_name;
    private String checkin_date, checkout_date;
    private float amout;

    public RoomInvoiceItem(int id, String room_name,  String checkin_date, String checkout_date, float amout) {
        this.id = id;
        this.room_name = room_name;
        this.checkin_date = checkin_date;
        this.checkout_date = checkout_date;
        this.amout = amout;
    }

    public RoomInvoiceItem() {

    }

    @Override
    public String toString() {
        return "RoomInvoiceItem{" +
                "id=" + id +
                ", room_name='" + room_name + '\'' +
                ", checkin_date='" + checkin_date + '\'' +
                ", checkout_date='" + checkout_date + '\'' +
                ", amout=" + amout +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }


    public String getCheckin_date() {
        return checkin_date;
    }

    public void setCheckin_date(String checkin_date) {
        this.checkin_date = checkin_date;
    }

    public String getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(String checkout_date) {
        this.checkout_date = checkout_date;
    }

    public float getAmout() {
        return amout;
    }

    public void setAmout(float amout) {
        this.amout = amout;
    }
}
