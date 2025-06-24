package com.palakyadav.bytemegui;

import java.io.IOException;

public interface User {
    public abstract void displayMenu() throws MenuFullException, CartCapacityFullException, IOException;
}
