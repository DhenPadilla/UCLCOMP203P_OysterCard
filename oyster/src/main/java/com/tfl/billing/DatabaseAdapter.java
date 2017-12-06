package com.tfl.billing;

import com.oyster.OysterCard;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class DatabaseAdapter implements DatabaseInterface {

    private static DatabaseAdapter instance = new DatabaseAdapter();

    private List<Customer> customers = CustomerDatabase.getInstance().getCustomers();

    /*private List<Customer> customers = new ArrayList<Customer>() {
        {
            this.add(new Customer("Fred Bloggs", new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d")));
            this.add(new Customer("Shelly Cooper", new OysterCard("3f1b3b55-f266-4426-ba1b-bcc506541866")));
            this.add(new Customer("Oliver Morrell", new OysterCard("07b0bcb1-87df-447f-bf5c-d9961ab9d01e")));
            this.add(new Customer("Jesse Schmitz", new OysterCard("3b5a03cb-2be6-4ed3-b83e-94858b43e407")));
            this.add(new Customer("Jenny King", new OysterCard("89adbd1c-4de6-40e5-98bc-b3315c6873f2")));
            this.add(new Customer("Danny Conner", new OysterCard("609e72ac-8be3-4476-8b45-01db8c7f122b")));
            this.add(new Customer("Fern Taylor", new OysterCard("ffa3f53a-e225-43ea-b854-5130a1fa016d")));
            this.add(new Customer("Logan Wakefield", new OysterCard("cd5e097d-e2f1-4bc4-bf7b-d47903d89441")));
            this.add(new Customer("Angela Harris", new OysterCard("365d7f7d-0ff5-4f87-a51d-f6443a36d035")));
            this.add(new Customer("John Smith", new OysterCard("0c79d9e0-3874-4b02-9492-80c232a07640")));
            this.add(new Customer("Mary Clarke", new OysterCard("34bbfc54-916b-42b2-a6d8-5ec2eaf7dd7a")));
            this.add(new Customer("Emily Scott", new OysterCard("267b3378-592d-4da7-825d-3252982d49ba")));
            this.add(new Customer("Yeet Boi", new OysterCard("19aedd1c-4d23-40e5-92bc-b3315c1473f2")));
        }
    };
    */

    public static DatabaseAdapter getInstance() {
        return instance;
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    public void add(String fullName){
        UUID OysterID = UUID.randomUUID();
        while (customers.contains(OysterID)) {
            OysterID = UUID.randomUUID();
        }
        Customer customer = new Customer(fullName, new OysterCard(OysterID.toString()));
        customers.add(customer);
    }

    @Override
    public boolean isRegisteredId(UUID cardId) {
        Iterator i = this.customers.iterator();
        Customer customer;
        do {
            if (!i.hasNext()) {
                return false;
            }

            customer = (Customer)i.next();
        } while(!customer.cardId().equals(cardId));

        return true;
    }
}
