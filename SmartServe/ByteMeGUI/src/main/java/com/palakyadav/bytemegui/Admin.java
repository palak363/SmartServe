package com.palakyadav.bytemegui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class Admin implements User, Serializable {
    private transient Scanner scanner = new Scanner(System.in);
    private Login login;
    public static TreeMap<Integer, Item> menu = new TreeMap<>();
    public static TreeMap<Integer, Item> temp = new TreeMap<>();
    public static PriorityQueue<Cart> orders = new PriorityQueue<>();
    public static int orderCount = 1;
    private static final int MAX_MENU_SIZE = 100;

    private static final String pendingOrdersFile = "PendingOrders.ser";
    private static final String MENU = "menu.ser";

    public Admin(Login login) throws IOException {
        this.login = login;
        menu.put(111,new Item(111,"Paratha","meals",50,true));
        menu.put(222,new Item(222,"Maggie","snacks",25,true));
        menu.put(333,new Item(333,"Dosa","meals",100,false));
        menu.put(444,new Item(444,"Coffee","beverages",80,true));
        loadPendingOrders();
        loadMenu();

    }

    public Admin() throws IOException {
        menu.put(111,new Item(111,"Paratha","meals",50,true));
        menu.put(222,new Item(222,"Maggie","snacks",25,true));
        menu.put(333,new Item(333,"Dosa","meals",100,false));
        menu.put(444,new Item(444,"Coffee","beverages",80,true));
        loadPendingOrders();
        loadMenu();
    }

    public void GUImenu() {

        Stage stage = new Stage();
        stage.setTitle("Menu");

        TableView<MenuItemDetail> tableView = new TableView<>();
        ObservableList<MenuItemDetail> items = FXCollections.observableArrayList();

        for (Map.Entry<Integer, Item> entry : menu.entrySet()) {
            System.out.println("-------");

            int id = entry.getKey();
            Item item = entry.getValue();
            String name = item.getName();
            double price = item.getPrice();
            String category = item.getCategory();
            boolean availability = item.getAvailability();

            System.out.println("ITEM ID: " + id + ", NAME: " + item.getName() + ", PRICE: " + item.getPrice() + ", CATEGORY: " + item.getCategory() + ", AVAILABILITY:" + item.getAvailability());

            MenuItemDetail menuItemDetail = new MenuItemDetail(id, name, price, category, availability);
            items.add(menuItemDetail);
        }

        // Table columns
        TableColumn<MenuItemDetail, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MenuItemDetail, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<MenuItemDetail, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<MenuItemDetail, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<MenuItemDetail, Boolean> availabilityColumn = new TableColumn<>("Available");
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        // Adding columns to the table
        tableView.getColumns().addAll(idColumn, nameColumn,  priceColumn, categoryColumn,availabilityColumn);

        // Adding items to the table
        tableView.setItems(items);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            stage.close();
            new GUI().start(new Stage());
        });

        // Layout and scene
        VBox vbox = new VBox(10, tableView, backButton);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 600, 400);

        stage.setScene(scene);
        stage.show();
    }

    public static class MenuItemDetail {
        private final int id;
        private final String name;
        private final double price;
        private final String category;
        private final Boolean availability;

        public MenuItemDetail(int id, String name, double price, String category, Boolean availability) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.category = category;
            this.availability = availability;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getCategory() {
            return category;
        }

        public Boolean getAvailability() {
            return availability;
        }
    }

    public void GUIpendingOrders(){
        Stage stage = new Stage();
        stage.setTitle("Pending Orders");

        TableView<PendingOrderDetail> tableView = new TableView<>();
        ObservableList<PendingOrderDetail> orders = FXCollections.observableArrayList();

        for (Cart order : Admin.orders) {
            if (order.getStatus().equalsIgnoreCase("pending")) {
                int orderId = order.getOrderId();
                HashMap<Item, Integer> items = order.getItems();
                String status = order.getStatus();

                for (Map.Entry<Item, Integer> entry : items.entrySet()) {
                    Item item = entry.getKey();
                    int quantity = entry.getValue();
                    orders.add(new PendingOrderDetail(orderId, item.getName(), quantity, status));
                }
            }
        }

        //Table columns
        TableColumn<PendingOrderDetail, Integer> orderIdColumn = new TableColumn<>("Order ID");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<PendingOrderDetail, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<PendingOrderDetail, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<PendingOrderDetail, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.getColumns().addAll(orderIdColumn, itemNameColumn, quantityColumn, statusColumn);

        tableView.setItems(orders);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            stage.close();
            new GUI().start(new Stage());
        });

        VBox vbox = new VBox(10, tableView, backButton);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 600, 400);

        stage.setScene(scene);
        stage.show();
    }


    public static class PendingOrderDetail {
        private final int orderId;
        private final String itemName;
        private final int quantity;
        private final String status;

        public PendingOrderDetail(int orderId, String itemName, int quantity, String status) {
            this.orderId = orderId;
            this.itemName = itemName;
            this.quantity = quantity;
            this.status = status;
        }

        public int getOrderId() {
            return orderId;
        }

        public String getItemName() {
            return itemName;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getStatus() {
            return status;
        }
    }


    public static TreeMap<Integer, Item> getMenu() {
        return menu;
    }

    public static void setMenu(TreeMap<Integer, Item> menu) {
        Admin.menu = menu;
    }

    public static void savePendingOrders() throws IOException {
        FileOutputStream file = new FileOutputStream(pendingOrdersFile);
        ObjectOutputStream out = new ObjectOutputStream(file);
        try (out) {
            PriorityQueue<Cart> allOrders = new PriorityQueue<>();
            for (Cart order : orders) {
                if (order.getStatus().equalsIgnoreCase("pending")) {
                    allOrders.add(order);
                }
            }out.writeObject(allOrders);
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }


    static void loadPendingOrders() throws IOException {
        File file = new File(pendingOrdersFile);
        if (!file.exists()) {
            orders = new PriorityQueue<>();
            return;
        }

        try (FileInputStream fileStream = new FileInputStream(pendingOrdersFile);
             ObjectInputStream in = new ObjectInputStream(fileStream)) {
            orders = (PriorityQueue<Cart>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
    }


    public static void saveMenu() throws IOException {
        FileOutputStream file = new FileOutputStream(MENU);
        ObjectOutputStream out = new ObjectOutputStream(file);
        try (out) {
            out.writeObject(menu);
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }


    static void loadMenu() throws IOException {
        File file = new File(MENU);
        if (!file.exists()) {
            menu = new TreeMap<>();
            menu.put(111,new Item(111,"Paratha","meals",50,true));
            menu.put(222,new Item(222,"Maggie","snacks",25,true));
            menu.put(333,new Item(333,"Dosa","meals",100,false));
            menu.put(444,new Item(444,"Coffee","beverages",80,true));

            return;
        }

        try (FileInputStream fileStream = new FileInputStream(MENU);
             ObjectInputStream in = new ObjectInputStream(fileStream)) {
            menu = (TreeMap<Integer, Item>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading menu: " + e.getMessage());
        }
    }


    public void displayMenu() throws MenuFullException, CartCapacityFullException {
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
        System.out.println("\nAdmin Interface:");
        System.out.println("1. Menu Management");
        System.out.println("2. Order Management");
        System.out.println("3. Report Generation");
        System.out.println("4. Back to Main Menu");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                this.menuManagement();
                break;
            case "2":
                this.orderManagement();
                break;
            case "3":
                this.reportGeneration();
                break;
            case "4":
                try {
                    login.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        displayMenu();
    }

    public void menuManagement() throws MenuFullException {
            String choice;
            do {
                System.out.println("\n1. Add new items");
                System.out.println("2. Update existing items");
                System.out.println("3. Remove items");
                System.out.println("4. Back");
                choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        this.addItem();
                        break;
                    case "2":
                        this.updateItem();
                        break;
                    case "3":
                        this.removeItem();
                        break;
                    case "4":
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } while (!choice.equals("4"));
        }

    //adding item to menu
    private void addItem() {
        try {
            if (menu.size() >= MAX_MENU_SIZE) {
                throw new MenuFullException("The menu is full. Cannot add more items.");
            }

            System.out.println("Enter item ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter item name: ");
            String name = scanner.nextLine();
            System.out.println("Enter item category: ");
            String category = scanner.nextLine();
            System.out.println("Enter item price: ");
            double price = scanner.nextDouble();
            System.out.println("Is the item available (true/false)? ");
            boolean available = scanner.nextBoolean();

            Item item = new Item(id, name, category, price, available);
            menu.put(id, item);
            System.out.println("Item added successfully.");
            saveMenu();
        } catch (MenuFullException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //update details of existing items in menu
    private void updateItem() {
        try{
            System.out.println("Enter ID of the item you would like to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            if (menu.containsKey(id)) {
                System.out.println("What do you want to update: 1.Name  2.Category  3.Price  4.Availability status");
                String choice = scanner.nextLine();
                Item item = menu.get(id);

                switch (choice) {
                    case "1":
                        System.out.println("Enter new name: ");
                        String name = scanner.nextLine();
                        item.setName(name);
                        break;
                    case "2":
                        System.out.println("Enter new category: ");
                        String category = scanner.nextLine();
                        item.setCategory(category);
                        break;
                    case "3":
                        System.out.println("Enter new price: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();
                        item.setPrice(price);
                        break;
                    case "4":
                        System.out.println("Enter new availability status: ");
                        boolean available = scanner.nextBoolean();
                        item.setAvailability(available);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
                saveMenu();
                System.out.println("Item updated successfully.");
            } else {
                System.out.println("Item does not exist.");
            }
        }catch (InputMismatchException | IOException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        }
    }

    //remove item from menu
    private void removeItem() {
        try{
            System.out.println("Enter ID of item to be removed: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            //BONUS
            for(Cart c: orders){
                if(c.getOrderId() == id){
                    c.setStatus("Denied");
                    break;
                }
            }
            if (menu.remove(id) != null) {
                saveMenu();
                System.out.println("Item removed successfully.");
            } else {
                System.out.println("Item does not exist.");
            }
        }catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void orderManagement() {
        String choice;
        do {
            System.out.println("\n1. View pending orders");
            System.out.println("2. Update order status");
            System.out.println("3. Process refunds");
            System.out.println("4. Handle special requests");
            System.out.println("5. Back");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    this.viewPendingOrders();
                    break;
                case "2":
                    this.updateOrderStatus();
                    break;
                case "3":
                    this.processRefunds();
                    break;
                case "4":
                    this.handleSpecialRequests();
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!choice.equals("5"));
    }

    //view all orders that are pending
    private void viewPendingOrders() {
        System.out.println("Pending Order IDs: ");
        for (Cart order : orders) {
            if (order.getStatus().equalsIgnoreCase("pending")) {
                System.out.println("ORDER ID- " + order.getOrderId());
                HashMap<Item, Integer> item = order.getItems();
                for (Map.Entry<Item, Integer> entry : item.entrySet()) {
                    Item itemName = entry.getKey();
                    Integer quantity = entry.getValue();
                    System.out.println("Item: " + itemName.getName() + ", Quantity: " + quantity);
                }
                System.out.println("STATUS- " + order.getStatus());

                System.out.println("-------------------------");

            }
        }
    }

    //update the status for any order
    private void updateOrderStatus() {
        try{
            System.out.println("Enter Order ID to update status: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            for(Cart order : orders) {
                if (order.getOrderId() == id) {
                    System.out.println("Enter new status (preparing / out for delivery / completed): ");
                    String status = scanner.nextLine();
                    order.setStatus(status);
                    System.out.println("Order status updated successfully.");
                    savePendingOrders();
                    return;
                }
            }System.out.println("Order does not exist.");
        }catch (InputMismatchException | IOException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        }

    }

    //process refund for any order
    private void processRefunds() {
        try {
            System.out.println("Enter Order ID to process refund: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Process refunds (yes/no): ");
            String status = scanner.nextLine();
            boolean flag = false;

            if (status.equalsIgnoreCase("yes")) {
                for (Cart order : orders) {
                    if (order.getOrderId() == id) {
                        order.setStatus("Process refund");
                        System.out.println("Processed refund successfully.");
                        flag = true;
                        savePendingOrders();
                        break;
                    }
                }
            } else {
                System.out.println("No refund processed.");
                flag = true;
            }

            if (!flag) {
                System.out.println("Order ID not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //take care of special requests of customers
    private void handleSpecialRequests() {
        try {
            System.out.println("Enter Order ID to handle special request: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            boolean orderFound = false;

            for (Cart order : orders) {
                if (order.getOrderId() == id) {
                    System.out.println("Special Request:" + order.getSpecialRequest());
                    orderFound = true;
                }
            }

            if (!orderFound) {
                System.out.println("Order ID not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please ensure you enter the correct data types.");
            scanner.nextLine();
        }
    }


    public void reportGeneration() {
        double totalSales = 0;
        int totalOrders = 0;
        Map<Item, Integer> itemOrderCount = new HashMap<>();

        for (Cart order : orders) {
            totalSales += order.getTotalAmount();
            totalOrders++;
            for (Map.Entry<Item, Integer> entry : order.getItems().entrySet()) {
                Item item = entry.getKey();
                int quantity = entry.getValue();
                itemOrderCount.put(item, itemOrderCount.getOrDefault(item, 0) + quantity);
            }
        }

        Item mostPopularItem = null;
        int highestCount = 0;
        for (Map.Entry<Item, Integer> entry : itemOrderCount.entrySet()) {
            int count = entry.getValue();
            Item item = entry.getKey();
            if (count > highestCount) {
                mostPopularItem = item;
                highestCount = count;
            }
        }

        System.out.println("Total Sales: " + totalSales);
        System.out.println("Total Orders: " + totalOrders);

        if (mostPopularItem != null && menu.containsKey(mostPopularItem.getId())) {
            System.out.println("Most Popular Item: " + mostPopularItem.getName());
        } else {
            System.out.println("No items have been ordered yet.");
        }
    }

}

