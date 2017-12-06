package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
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
        db.add("C K");

        String custExpected = "John Smith";
        String custExpected1 = "C K";

        Customer customer = db.getCustomers().get(size);
        Customer customer1 = db.getCustomers().get((size + 1));

        String custActual = customer.fullName();
        String custActual1 = customer1.fullName();

        assertEquals(custExpected, custActual);
        assertEquals(custExpected1, custActual1);
    }


    @Test
    public void checkIfRegisteredIdMethod() {
        assertTrue(db.isRegisteredId(UUID.fromString("3f1b3b55-f266-4426-ba1b-bcc506541866")));
    }
}
