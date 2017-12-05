package com.tfl.billing;

import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;
import org.junit.Test;
import com.oyster.*;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import com.tfl.external.PaymentsSystem;
import org.mockito.Mockito;
import com.oyster.OysterCardReader;

import static org.junit.Assert.*;

public class TravelTrackerTest {

    @Test
    public void connect() throws Exception {
        TravelTracker mockTracker = Mockito.spy(new TravelTracker());
        OysterCardReader paddingtonMockReader = Mockito.spy(OysterReaderLocator.atStation(Station.PADDINGTON));

        mockTracker.connect(paddingtonMockReader);
        Mockito.verify(paddingtonMockReader).register(mockTracker);
    }

    @Test
    public void oldSystemOffPeakTest() {
        fail("TODO");
    }

    @Test
    public void oldSystemPeakTest() {
        fail("TODO");
    }
}