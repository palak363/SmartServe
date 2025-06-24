package com.palakyadav.bytemegui;

import java.io.IOException;

public class CLI {
    public static void main(String[] args) throws MenuFullException, CartCapacityFullException, IOException {
        try{
            Admin.loadMenu();
            Customer.loadCustomers();
            Admin.loadPendingOrders();
            Login login = new Login();
            login.start();
        }catch (IOException e) {
            System.err.println("An error occurred while loading the menu: " + e.getMessage());
        }
    }
}
