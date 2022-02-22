package com.example.hoteleye.databases.entities;

import java.io.Serializable;

public class InventoryItem implements Serializable {
    private String inventory_id;
    private String inventory_name;
    private String import_date; //ngày nhập
    private int import_total;  // tổng số lượng nhập
    private ServiceItem serviceItem;
    private String description;

    public InventoryItem(String inventory_id, String inventory_name, String import_date, int import_total, ServiceItem serviceItem, String description) {
        this.inventory_id = inventory_id;
        this.inventory_name = inventory_name;
        this.import_date = import_date;
        this.import_total = import_total;
        this.serviceItem = serviceItem;
        this.description = description;
    }

    public String getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(String inventory_id) {
        this.inventory_id = inventory_id;
    }

    public String getInventory_name() {
        return inventory_name;
    }

    public void setInventory_name(String inventory_name) {
        this.inventory_name = inventory_name;
    }

    public String getImport_date() {
        return import_date;
    }

    public void setImport_date(String import_date) {
        this.import_date = import_date;
    }

    public int getImport_total() {
        return import_total;
    }

    public void setImport_total(int import_total) {
        this.import_total = import_total;
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
