package com.epam.sultangazy.webapp.entity;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int id;
    private int restaurant;
    private int user;
    private int amount;
    private String address;
    private String phone;
    private Timestamp datetime;
    private String status;
    private List<Dish> foods;

    public Order() {
    }

    public Order(int id, int restaurant, int user, int amount, String address, String phone, Timestamp datetime, String status, List<Dish> foods) {
        this.id = id;
        this.restaurant = restaurant;
        this.user = user;
        this.amount = amount;
        this.address = address;
        this.phone = phone;
        this.datetime = datetime;
        this.status = status;
        this.foods = foods;
    }

    @Override
    public String toString() {
        return Order.class.toString();
    }

    public List<Dish> getFoods() {
        return foods;
    }

    public void setFoods(List<Dish> foods) {
        this.foods = foods;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(int restaurant) {
        this.restaurant = restaurant;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
