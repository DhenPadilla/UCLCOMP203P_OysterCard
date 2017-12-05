package com.tfl.billing;

import com.oyster.OysterCard;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CostCalculatorTestOld {

    private List<Journey> addJourneys(boolean peak, OysterCardReader mockReader) {
        List<Journey> journeys = new ArrayList<Journey>();

        List<Customer> customers = CustomerDatabase.getInstance().getCustomers();

        HashMap<Integer, OysterCardReader> readers = addReaders(mockReader);

        for (int i = 0; i < 2; i += 2) {
            JourneyStart start = new JourneyStart(customers.get(i).cardId(), readers.get(i).getReaderId());
            JourneyEnd end = new JourneyEnd(customers.get(i).cardId(), readers.get((i + 1)).getReaderId());
            if (peak) {
                journeys.add(new Journey.JourneyBuilder(start, end).setStartTime(18).build());
            }
            else {
                journeys.add(new Journey.JourneyBuilder(start, end).setStartTime(13).build());
            }
        }

        return journeys;
    }

    private HashMap<Integer, OysterCardReader> addReaders(OysterCardReader mockReader) {
        HashMap<Integer, OysterCardReader> readers = new HashMap<Integer, OysterCardReader>();

        when(mockReader.getReaderId()).thenReturn(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));

        for(int i = 0 ; i < 2; i++) {
            readers.put(i, mockReader);
        }
        return readers;
    }

    @Test
    public void checkForCorrectCalculationOldSinglePeakShort() {
        OysterCardReader mockReader = mock(OysterCardReader.class);
        List<Journey> journeys = addJourneys(true, mockReader);

        CostCalculator calculator = new CostCalculator(journeys);

        double totalPrice = calculator.getOldCustomerTotal().doubleValue();
        double actualPrice = 3.20;

        assertEquals(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), mockReader.getReaderId());
        assertEquals(totalPrice, actualPrice, 0);
    }

    @Test
    public void checkForCorrectCalculationOldSingleOffPeakShort() {
        OysterCardReader mockReader = mock(OysterCardReader.class);
        List<Journey> journeys = addJourneys(false, mockReader);

        CostCalculator calculator = new CostCalculator(journeys);

        double totalPrice = calculator.getOldCustomerTotal().doubleValue();
        double actualPrice = 2.40;

        assertEquals(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), mockReader.getReaderId());
        assertEquals(totalPrice, actualPrice, 0);
    }
}
