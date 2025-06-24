package com.palakyadav.bytemegui;

import java.io.Serializable;

public class Item implements Comparable<Item> , Serializable {
    int id;
    String name;
    String category;
    double price;
    boolean availability;

    public Item(int id,String name, String category, double price, boolean available) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.availability = available;
        this.id = id;
    }

    public boolean isAvailability() {
        return availability;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return name + " (" + category + ") - $" + price + " - " + (availability ? "Available" : "Not Available");
    }

    @Override
    public int compareTo(Item x) {
        if(this.price > x.getPrice()) return 1;
        else if(this.price < x.getPrice()) return -1;
        else return 0;
    }
}
