package com.palakyadav.bytemegui;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

public class Login implements Serializable {
    static HashMap<String, Customer> customerMap;
    private transient Scanner scanner;
    private Admin admin;


    public Login() throws IOException {
        this.customerMap = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.admin = new Admin(this);
        Customer.loadCustomers();
    }


    public Login(HashMap<String, Customer> customerMap, Scanner scanner, Admin admin) throws IOException {
        this.customerMap = customerMap;
        this.scanner = scanner;
        this.admin = admin;
        Customer.loadCustomers();
    }

    public void start() throws MenuFullException, CartCapacityFullException, IOException {
        while (true) {
            if (this.scanner == null) {
                this.scanner = new Scanner(System.in);
            }
            System.out.println("\nCanteen System - Choose an interface:");
            System.out.println("1. Admin Interface");
            System.out.println("2. Customer Interface");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    admin.displayMenu();
                    break;
                case 2:
                    handleCustomerLogin();
                    break;
                case 3:
                    Admin.saveMenu();
                    Admin.savePendingOrders();
                    Customer.saveCustomers();
                    System.out.println("Exited, Thank you for using our system.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    void handleCustomerLogin() throws MenuFullException, CartCapacityFullException {
        System.out.println("Enter your customer ID (or enter 'new' to create a new account): ");
        String idInput = scanner.nextLine();

        if (idInput.equalsIgnoreCase("new")) {
            System.out.println("Enter a new customer ID: ");
            String newId = scanner.nextLine();
            if (customerMap.containsKey(newId)) {
                System.out.println("ID already exists. Please try again.");
            } else {
                try {
                    Customer newCustomer = new Customer(newId, this);
                    customerMap.put(newId, newCustomer);
                    System.out.println("Account created. Welcome!");
                    newCustomer.displayMenu();
                } catch (IOException e) {
                    System.out.println("Error creating new customer: " + e.getMessage());
                }
            }
        } else {
            Customer customer = customerMap.get(idInput);
            if (customer != null) {
                System.out.println("Welcome back!");
                try {
                    customer.displayMenu();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("ID not found. Please try again.");
            }
        }
    }

}
