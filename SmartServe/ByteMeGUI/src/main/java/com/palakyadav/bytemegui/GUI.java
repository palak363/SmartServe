package com.palakyadav.bytemegui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button menu = new Button("View Menu");
        Button pendingOrders = new Button("View Pending Orders");
        Button exit = new Button("Exit");

        menu.setOnAction(e -> {
            try {
                new Admin().GUImenu();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        pendingOrders.setOnAction(e -> {
            try {
                new Admin().GUIpendingOrders();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        exit.setOnAction(e -> {
            Platform.exit();
        });


        VBox layout = new VBox(20);
        layout.getChildren().addAll(menu, pendingOrders, exit);

        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 223, 400);
        primaryStage.setTitle("Byte Me!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
