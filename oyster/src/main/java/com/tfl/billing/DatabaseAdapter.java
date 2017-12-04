package com.tfl.billing;

import com.tfl.external.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class DatabaseAdapter implements DatabaseInterface{

    private List<Customer> customers = new ArrayList<Customer>();

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    public void add(Customer customer){
        customers.add(customer);
    }
}
