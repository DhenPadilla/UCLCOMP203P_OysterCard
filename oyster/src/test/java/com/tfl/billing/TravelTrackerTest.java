package com.tfl.billing;

import com.tfl.external.Customer;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TravelTrackerTest {

    OysterCardReader reader = mock(OysterCardReader.class);

    @Test
    public void connect() throws Exception {}

    @Test
    public void chargeAccountsMethodTest() {
        TravelTracker tracker = new TravelTracker();
        tracker.chargeAccounts();
    }

    @Test
    public void cardScannedCurrentlyTravellingTest() {
        DatabaseAdapter database = new DatabaseAdapter();

        when(reader.getReaderId()).thenReturn(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));

        List<Customer> customers = database.getCustomers();

        TravelTracker tracker = new TravelTracker();
        boolean res = tracker.cardScanned(customers.get(0).cardId(), reader.getReaderId());

        assertTrue(res);
    }
}