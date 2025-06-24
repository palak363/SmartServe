package com.palakyadav.bytemegui;

import java.io.*;
import java.util.*;

public class Customer implements User , Serializable {
    private transient Scanner scanner = new Scanner(System.in);
    private Login login;
    private HashMap<Item, Integer> orderCart = new HashMap<>();
    private static final int MAX_CART_SIZE = 100000;
    private String customerId;
    private ArrayList<Cart> myOrders = new ArrayList<>();
    private static final String customerList = "customerList.ser";

    public Customer(String customerId ,Login login) throws IOException {
        this.customerId = customerId;
        this.login = login;
        Admin.loadMenu();
    }

    public Customer(String customerId) throws IOException {
        this.customerId = customerId;
        Admin.loadMenu();
    }

    public static void saveCustomers() throws IOException {
        FileOutputStream file = new FileOutputStream(customerList);
        ObjectOutputStream out = new ObjectOutputStream(file);
        try (out) {
            out.writeObject(Login.customerMap);
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }


    static void loadCustomers() throws IOException {
        File file = new File(customerList);
        if (!file.exists()) {
            Login.customerMap = new HashMap<String, Customer>();
            return;
        }

        try (FileInputStream fileStream = new FileInputStream(customerList);
             ObjectInputStream in = new ObjectInputStream(fileStream)) {
            Login.customerMap = (HashMap<String, Customer>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading menu: " + e.getMessage());
        }
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void displayMenu() throws MenuFullException, CartCapacityFullException, IOException {
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
        System.out.println("\nCustomer Interface:");
        System.out.println("1. Browse Menu");
        System.out.println("2. Cart Operations");
        System.out.println("3. Order Tracking");
        System.out.println("4. Item Reviews");
        System.out.println("5. Back to Main Menu");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                this.browseMenu();
                break;
            case "2":
                this.cartOperations();
                break;
            case "3":
                this.orderTracking();
                break;
            case "4":
                this.itemReviews();
                break;
            case "5":
                this.login.start();
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        displayMenu();
    }

    public void browseMenu() {
            String choice;
            do {
                System.out.println("\n1. View all items");
                System.out.println("2. Search");
                System.out.println("3. Filter by category");
                System.out.println("4. Sort by price");
                System.out.println("5. Back");
                choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        this.viewItem();
                        break;
                    case "2":
                        this.search();
                        break;
                    case "3":
                        this.filterByCategory();
                        break;
                    case "4":
                        this.sortByPrice();
                        break;
                    case "5":
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } while (!choice.equals("5"));
        }

    //view menu
    public void viewItem() {
        for (Map.Entry<Integer, Item> entry : Admin.menu.entrySet()) {
            int id = entry.getKey();
            Item item = entry.getValue();
            System.out.println("ITEM ID: " + id + ", NAME: " + item.getName() + ", PRICE: " + item.getPrice() + ", CATEGORY: " + item.getCategory() + ", AVAILABILITY:" + item.getAvailability());
        }
    }

    //search for item in menu using name
    private void search() {
        try{
            System.out.println("Enter name of item to search: ");
            String name = scanner.nextLine();
            for (Map.Entry<Integer, Item> entry : Admin.menu.entrySet()) {
                Item item = entry.getValue();
                if(item.getName().toLowerCase().contains(name.toLowerCase())) {
                    System.out.println("ITEM ID: " + item.getId() + ", NAME: " + item.getName() + ", PRICE: " + item.getPrice() + ", CATEGORY: " + item.getCategory() + ", AVAILABILITY:" + item.getAvailability());
                    return;
                }
            }System.out.println("Item not found.");
        }catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        }
    }

    //filter items by choosing category
    private void filterByCategory() {
        try{
            int flag=0;
            System.out.println("Enter category to filter: 1. Snacks  2.Beverages  3.Meals ");
            String category = scanner.nextLine();
            switch (category) {
                case "1":
                    for (Map.Entry<Integer, Item> entry : Admin.menu.entrySet()) {
                        Item item = entry.getValue();
                        if(item.getCategory().equalsIgnoreCase("snacks")) {
                            flag = 1;
                            System.out.println("ITEM ID: " + item.getId() + ", NAME: " + item.getName() + ", PRICE: " + item.getPrice() + ", CATEGORY: " + item.getCategory() + ", AVAILABILITY:" + item.getAvailability());
                        }
                    }if(flag == 0) {System.out.println("No item found.");}
                    break;
                case "2":
                    for (Map.Entry<Integer, Item> entry : Admin.menu.entrySet()) {
                        Item item = entry.getValue();
                        if(item.getCategory().equalsIgnoreCase("beverages")) {
                            flag = 1;
                            System.out.println("ITEM ID: " + item.getId() + ", NAME: " + item.getName() + ", PRICE: " + item.getPrice() + ", CATEGORY: " + item.getCategory() + ", AVAILABILITY:" + item.getAvailability());
                        }
                    }if(flag == 0) {System.out.println("No item found.");}
                    break;
                case "3":
                    for (Map.Entry<Integer, Item> entry : Admin.menu.entrySet()) {
                        Item item = entry.getValue();
                        if(item.getCategory().equalsIgnoreCase("meals")) {
                            flag = 1;
                            System.out.println("ITEM ID: " + item.getId() + ", NAME: " + item.getName() + ", PRICE: " + item.getPrice() + ", CATEGORY: " + item.getCategory() + ", AVAILABILITY:" + item.getAvailability());
                        }
                    }if(flag == 0) {System.out.println("No item found.");}
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        }
    }

    //sort menu based on price
    private void sortByPrice() {
        System.out.println("Sort in: 1.Ascending or 2.Descending ");
        String choice = scanner.nextLine();
        List<Item> items = new ArrayList<>(Admin.menu.values());
        switch (choice) {
            case "1":
                Collections.sort(items);
                for (Item item : items) {
                    System.out.println("ITEM ID: " + item.getId() + ", NAME: " + item.getName() +
                            ", PRICE: " + item.getPrice() + ", CATEGORY: " + item.getCategory() +
                            ", AVAILABILITY: " + item.getAvailability());
                }break;
            case "2":
                items.sort(Collections.reverseOrder());
                for (Item item : items) {
                    System.out.println("ITEM ID: " + item.getId() + ", NAME: " + item.getName() +
                            ", PRICE: " + item.getPrice() + ", CATEGORY: " + item.getCategory() +
                            ", AVAILABILITY: " + item.getAvailability());
                }break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }


    public void cartOperations() throws CartCapacityFullException {
        String choice;
        do {
            System.out.println("\n1. Add items");
            System.out.println("2. Modify quantities");
            System.out.println("3. Remove items");
            System.out.println("4. View total");
            System.out.println("5. Checkout process");
            System.out.println("6. Back");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    this.addItem();
                    break;
                case "2":
                    this.modifyQuantity();
                    break;
                case "3":
                    this.removeItems();
                    break;
                case "4":
                    this.viewTotal();
                    break;
                case "5":
                    this.checkout();
                    break;
                case "6":
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!choice.equals("6"));
    }

    //add item to cart
    void addItem(int id, int quantity) {
        Item itemToAdd = Admin.menu.get(id);
        if (itemToAdd != null && itemToAdd.getAvailability()) {
            addToCart(itemToAdd, quantity);
        } else {
            System.out.println("Item not found or unavailable.");
        }
    }

    // Overloaded method to add item by name
    private void addItem(String name, int quantity) {
        Item itemToAdd = null;
        for (Item item : Admin.menu.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                itemToAdd = item;
                break;
            }
        }
        if (itemToAdd != null && itemToAdd.getAvailability()) {
            addToCart(itemToAdd, quantity);
        } else {
            System.out.println("Item not found or unavailable.");
        }
    }

    private void addToCart(Item item, int quantity) {
        if (quantity > 0) {
            orderCart.put(item, orderCart.getOrDefault(item, 0) + quantity);
            System.out.println("Item added successfully.");
        } else {
            System.out.println("Quantity must be greater than zero.");
        }
    }

    //add item to cart
    public void addItem() {
        try{
            System.out.println("Add item by: 1. ID  2. Name");
            String choice = scanner.nextLine();
            int quantity;

            switch (choice){
                case "1":
                    System.out.println("Enter quantity to add to cart: ");
                    quantity = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter item ID to add to cart: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    addItem(id, quantity);
                    break;
                case "2":
                    System.out.println("Enter quantity to add to cart: ");
                    quantity = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter item name to add to cart: ");
                    String name = scanner.nextLine();
                    addItem(name, quantity);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        }
    }

    //modify quantity of any item that was added to the cart
    private void modifyQuantity() {
        try{
            System.out.println("Enter item ID to modify quantity: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Item item = Admin.menu.get(id);
            if (item == null) {
                System.out.println("Item with the given ID does not exist.");
                return;
            }

            if (!orderCart.containsKey(item)) {
                System.out.println("This item is not currently in your cart.");
                return;
            }

            System.out.println("Enter new quantity : ");
            int newQuantity = scanner.nextInt();
            scanner.nextLine();

            if (newQuantity > 0) {
                orderCart.put(item, newQuantity);
                System.out.println("Quantity updated successfully.");
            } else {
                System.out.println("Invalid quantity.");
            }
        }catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        }
    }

    //remove item from cart
    private void removeItems() {
        try{
            System.out.println("Enter item ID to remove from cart: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Item item = Admin.menu.get(id);
            if (!orderCart.containsKey(item)) {
                System.out.println("Item not in cart.");
            }else{
                orderCart.remove(item);
                System.out.println("Item removed successfully");
            }
        }catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        }
    }

    //view cart total
    private void viewTotal() {
        double total = 0;
        for(Map.Entry<Item, Integer> entry : orderCart.entrySet()) {
            int quantity = entry.getValue();
            Item item = entry.getKey();
            double price = item.getPrice();
            total += price * quantity;
        }
        System.out.println("Total price: " + total);
    }

    //place an order
    private void checkout() throws CartCapacityFullException {
        if (orderCart.isEmpty()) {
            System.out.println("Your cart is empty. Add items before checkout.");
            return;
        }

        System.out.println("Enter delivery address: ");
        String address = scanner.nextLine();
        System.out.println("Enter payment method: ");
        String method = scanner.nextLine();

        //BONUS
        System.out.println("Are you: VIP or Regular");
        String customerType = scanner.nextLine();
        if(customerType.equalsIgnoreCase("VIP")) {
            System.out.println("Pay VIP fee of 500Rs on delivery? (yes/no)");
            String response = scanner.nextLine();
            if(response.equals("yes")) {
                System.out.println("Congratulations! You have become a VIP.");
            }else{
                customerType = "regular";
            }
        }
        Cart order = new Cart(Admin.orderCount++, new HashMap<>(orderCart), address, method, customerType);
        System.out.println("Any special request?(yes/no)");
        String response = scanner.nextLine();
        if(response.equals("yes")) {
            System.out.println("Enter the special request ");
            String request = scanner.nextLine();
            order.setSpecialRequest(request);
        }else{
            order.setSpecialRequest("No special request");
        }

        Admin.orders.add(order);
        this.myOrders.add(order);

        if (order.getTotalAmount() > MAX_CART_SIZE) {
            throw new CartCapacityFullException("Cart capacity has exceeded.");
        }
        orderCart.clear();
        System.out.println("Order ID:" + (Admin.orderCount - 1));
        System.out.println("Checkout successful. Your order has been placed.");
    }

    //track placed order details
    public void orderTracking() {
        String choice;
        do {
            System.out.println("\n1. View order status");
            System.out.println("2. Cancel order");
            System.out.println("3. Order history");
            System.out.println("4. Back");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    this.viewOrderStatus();
                    break;
                case "2":
                    this.cancelOrder();
                    break;
                case "3":
                    this.orderHistory();
                    break;
                case "4":
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!choice.equals("4"));
    }

    //view status of order placed
    private void viewOrderStatus() {
        try{
            System.out.println("Enter order ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Cart orderCart = findCartById(id);
            if(orderCart != null){
                System.out.println("Order status: " + orderCart.getStatus());
            }else{
                System.out.println("Order not found.");
            }

        }catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        }
    }

    //cancel order
    private void cancelOrder() {
        try{
            System.out.println("Enter order ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Cart orderCart = findCartById(id);
            if (orderCart != null && (orderCart.getStatus().equalsIgnoreCase("prepared") || orderCart.getStatus().equalsIgnoreCase("processed") || orderCart.getStatus().equalsIgnoreCase("pending"))) {
                Admin.orders.remove(orderCart);
                this.myOrders.remove(orderCart);
                System.out.println("Order cancelled");
            } else {
                System.out.println("Order can't be cancelled");
            }
        }catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        }
    }

    //view your order history
    private void orderHistory() {
        int flag =0;
        for(Cart c : this.myOrders) {
            System.out.println(c.toString());
            flag++;
        }
        if(flag == 0){
            System.out.println("No orders");
        }
    }

    public void itemReviews() {
        String choice;
        do {
            System.out.println("\n1. Provide review");
            System.out.println("2. View reviews");
            System.out.println("3. Back");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    this.provideReview();
                    break;
                case "2":
                    this.viewReview();
                    break;
                case "3":
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!choice.equals("3"));
    }

    //give review of orders
    private void provideReview() {
        try{
            System.out.println("Enter order ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Cart orderCart = findCartById(id);
            if(orderCart != null){
                System.out.println("Enter review: ");
                String review = scanner.nextLine();
                orderCart.setReview(review);
                System.out.println("Review updated successfully.");
            }else{
                System.out.println("Order not found.");
            }

        }catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        }
    }

    //view review for orders
    private void viewReview() {
        try{
            System.out.println("Enter order ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Cart orderCart = findAnyCart(id);
            if(orderCart != null){
                System.out.println("Review: " + orderCart.getReview());
            }else{
                System.out.println("Order not found.");
            }

        }catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        }
    }

    public Cart findCartById(int orderId) {
        for (Cart cart : this.myOrders) {
            if (cart.getOrderId() == orderId) {
                return cart;
            }
        }
        return null;
    }

    public Cart findAnyCart(int orderId) {
        for (Cart cart : Admin.orders) {
            if (cart.getOrderId() == orderId) {
                return cart;
            }
        }
        return null;
    }

}

