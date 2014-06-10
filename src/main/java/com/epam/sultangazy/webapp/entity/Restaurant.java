package com.epam.sultangazy.webapp.entity;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private int id;
    private String name;
    private String image;
    private int deliveryTime;
    private String note;
    private int restorator;

    public Restaurant() {
    }

    public Restaurant(String name, int deliveryTime, String note, String image) {
        this.name = name;
        this.image = image;
        this.deliveryTime = deliveryTime;
        this.note = note;
    }

    public Restaurant(int id, String name, String image, int deliveryTime, String note, int restorator) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.deliveryTime = deliveryTime;
        this.note = note;
        this.restorator = restorator;
    }

    @Override
    public String toString() {
        return Restaurant.class.toString();
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRestorator() {
        return restorator;
    }

    public void setRestorator(int restorator) {
        this.restorator = restorator;
    }
}
