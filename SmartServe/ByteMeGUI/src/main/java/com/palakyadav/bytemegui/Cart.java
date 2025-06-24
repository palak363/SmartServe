package com.palakyadav.bytemegui;

import java.io.Serializable;
import java.util.HashMap;

public class Cart implements Comparable<Cart> , Serializable {
    private int orderId;
    private HashMap<Item, Integer> items; //item and its quantity
    private String status;
    private double totalAmount;
    private String refund;
    private String address;
    private String paymentMethod;
    private String review;
    private String specialRequest;
    private String customerType;

    public Cart(int orderId, HashMap<Item, Integer> items, String address, String paymentMethod, String customerType) {
        this.orderId = orderId;
        this.items = new HashMap<>(items);
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.totalAmount = calculateTotal();
        this.status = "Pending";
        this.review = "";
        this.specialRequest = "";
        this.customerType = customerType;
    }

    public Cart(){
    }

    private double calculateTotal() {
        double total = 0;
        for (Item item : items.keySet()) {
            int quantity = items.get(item);
            total += item.getPrice() * quantity;
        }
        return total;
    }



    @Override
    public String toString() {
        return "Order ID: " + orderId + ", Status: " + status + ", Total: " + totalAmount;
    }

    @Override
    public int compareTo(Cart other) {
        if (this.customerType.equalsIgnoreCase("VIP") && other.customerType.equalsIgnoreCase("Regular")) {
            return -1;
        } else if (this.customerType.equalsIgnoreCase("Regular") && other.customerType.equalsIgnoreCase("VIP")) {
            return 1;
        }
        // If both are the same type, sorting by orderId
        return this.orderId - other.orderId;
    }


    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public HashMap<Item, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<Item, Integer> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRefund() {
        return refund;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
