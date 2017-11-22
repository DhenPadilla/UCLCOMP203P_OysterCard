package com.tfl.billing;

import com.oyster.*;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.*;

public class TravelTracker implements ScanListener {

    static final BigDecimal OFF_PEAK_JOURNEY_PRICE = new BigDecimal(2.40);
    static final BigDecimal PEAK_JOURNEY_PRICE = new BigDecimal(3.20);

    // REQ: Long and Short journey prices or Peak and Off Peak
    static final BigDecimal OFF_PEAK_LONG_JOURNEY_PRICE = new BigDecimal(2.70);
    static final BigDecimal OFF_PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(1.60);
    static final BigDecimal PEAK_LONG_JOURNEY_PRICE = new BigDecimal(3.80);
    static final BigDecimal PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(2.90);

    // REQ: Daily Cap prices for Peak and Off Peak Journeys
    static final BigDecimal OFF_PEAK_DAILY_CAP = new BigDecimal(7.00);
    static final BigDecimal PEAK_DAILY_CAP = new BigDecimal(9.00);

    private final List<JourneyEvent> eventLog = new ArrayList<JourneyEvent>();
    private final Set<UUID> currentlyTravelling = new HashSet<UUID>();

    public void chargeAccounts() {
        CustomerDatabase customerDatabase = CustomerDatabase.getInstance();

        List<Customer> customers = customerDatabase.getCustomers();
        for (Customer customer : customers) {
            totalJourneysFor(customer);
        }
    }

    private void totalJourneysFor(Customer customer) {
        List<JourneyEvent> customerJourneyEvents = new ArrayList<JourneyEvent>();
        for (JourneyEvent journeyEvent : eventLog) {
            if (journeyEvent.cardId().equals(customer.cardId())) {
                customerJourneyEvents.add(journeyEvent);
            }
        }

        List<Journey> journeys = new ArrayList<Journey>();

        JourneyEvent start = null;
        for (JourneyEvent event : customerJourneyEvents) {
            if (event instanceof JourneyStart) {
                start = event;
            }
            if (event instanceof JourneyEnd && start != null) {
                journeys.add(new Journey(start, event));
                start = null;
            }
        }

        // Old System Calculations
        BigDecimal customerTotal = new BigDecimal(0);

        for (Journey journey : journeys) {
            BigDecimal journeyPrice = OFF_PEAK_JOURNEY_PRICE;
            if (peak(journey)) {
                journeyPrice = PEAK_JOURNEY_PRICE;
            }
            customerTotal = customerTotal.add(journeyPrice);
        }

        // REQ: Long and Short journey prices
        BigDecimal newCustomerTotal = new BigDecimal(0);
        int totPeakTrips = 0;

        for (Journey journey : journeys){
            BigDecimal journeyPrice = OFF_PEAK_SHORT_JOURNEY_PRICE;

            // Get duration of journey
            String journeyDurationStr = journey.durationMinutes();
            int journeyDuration = Integer.parseInt(journeyDurationStr.substring(0, journeyDurationStr.indexOf(":")));

            // Calculate costs
            if (peak(journey)) {
                journeyPrice = PEAK_SHORT_JOURNEY_PRICE;

                if (journeyDuration > 25) journeyPrice = PEAK_LONG_JOURNEY_PRICE;

                totPeakTrips++;
            }

            else if (journeyDuration > 25) journeyPrice = OFF_PEAK_LONG_JOURNEY_PRICE;

            newCustomerTotal = newCustomerTotal.add(journeyPrice);
        }

        // REQ: Daily Caps
        if (totPeakTrips > 0 && newCustomerTotal.compareTo(PEAK_DAILY_CAP) > 1) newCustomerTotal = PEAK_DAILY_CAP;
        else if (totPeakTrips == 0 && newCustomerTotal.compareTo(OFF_PEAK_DAILY_CAP) > 1) newCustomerTotal = OFF_PEAK_DAILY_CAP;

        // Old System
        //PaymentsSystem.getInstance().charge(customer, journeys, roundToNearestPenny(customerTotal));

        // New System
        PaymentsSystem.getInstance().charge(customer, journeys, roundToNearestPenny(newCustomerTotal));
    }

    private BigDecimal roundToNearestPenny(BigDecimal poundsAndPence) {
        return poundsAndPence.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private boolean peak(Journey journey) {
        return peak(journey.startTime()) || peak(journey.endTime());
    }

    private boolean peak(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return (hour >= 6 && hour <= 9) || (hour >= 17 && hour <= 19);
    }

    public void connect(OysterCardReader... cardReaders) {
        for (OysterCardReader cardReader : cardReaders) {
            cardReader.register(this);
        }
    }

    @Override
    public void cardScanned(UUID cardId, UUID readerId) {
        if (currentlyTravelling.contains(cardId)) {
            eventLog.add(new JourneyEnd(cardId, readerId));
            currentlyTravelling.remove(cardId);
        } else {
            if (CustomerDatabase.getInstance().isRegisteredId(cardId)) {
                currentlyTravelling.add(cardId);
                eventLog.add(new JourneyStart(cardId, readerId));
            } else {
                throw new UnknownOysterCardException(cardId);
            }
        }
    }

}
