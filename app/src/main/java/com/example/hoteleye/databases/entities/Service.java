package com.example.hoteleye.databases.entities;

public class Service {
    private String id;
    private ServiceItem serviceItem;
    private String name;
    private float unit_price;
    private String unity;
    private String type;
    private String description;

    public Service(String id, ServiceItem serviceItem, String name, float unit_price, String unity, String type, String description) {
        this.id = id;
        this.serviceItem = serviceItem;
        this.name = name;
        this.unit_price = unit_price;
        this.unity = unity;
        this.type = type;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id='" + id + '\'' +
                ", serviceItem=" + serviceItem +
                ", name='" + name + '\'' +
                ", unit_price=" + unit_price +
                ", unity='" + unity + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Service() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(float unit_price) {
        this.unit_price = unit_price;
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
