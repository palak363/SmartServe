package com.palakyadav.bytemegui;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class InvalidLoginTest {

    private Login loginSystem;

    @Before
    public void setUp() throws IOException {
        loginSystem = new Login(new HashMap<>(), null, new Admin(new Login()));
    }

    @Test
    public void testInvalidCustomerLogin() throws IOException {
        String invalidId = "nonExistentUser";
        Customer mockCustomer = loginSystem.customerMap.get(invalidId);

        assertNull("Customer should not be found", mockCustomer);
    }

    @Test
    public void testInvalidCustomerIdFormat() throws IOException {
        String invalidCustomerId = "!@#$%^&*()";

        Customer mockCustomer = loginSystem.customerMap.get(invalidCustomerId);

        assertNull("Customer should not be found due to invalid ID format", mockCustomer);
    }


}