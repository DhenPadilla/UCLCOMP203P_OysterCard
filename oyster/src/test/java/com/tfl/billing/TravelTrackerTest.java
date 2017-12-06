package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TravelTrackerTest {

    OysterCardReader reader = mock(OysterCardReader.class);

    @Test
    public void connect() throws Exception {
        TravelTracker tracker = new TravelTracker();
        OysterCardReader paddingtonMockReader = mock(OysterCardReader.class);

        tracker.connect(paddingtonMockReader);
        Mockito.verify(paddingtonMockReader).register(tracker);
    }

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