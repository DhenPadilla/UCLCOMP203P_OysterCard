package com.tfl.billing;

import com.tfl.external.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

public class DatabaseAdapterTest {

    DatabaseAdapter db = new DatabaseAdapter();
    List<Customer> customers = db.getCustomers();

    @Test
    public void checkIfAddMethodWorks() {
        int size = customers.size();
        db.add("John Smith");

        String custExpected = "John Smith";

        Customer customer = db.getCustomers().get(size);
        String custActual = customer.fullName();

        assertEquals(custExpected, custActual);
    }

    @Test
    public void checkIfRegisteredIdMethod() {
        assertTrue(db.isRegisteredId(UUID.fromString("3f1b3b55-f266-4426-ba1b-bcc506541866")));
    }
}
