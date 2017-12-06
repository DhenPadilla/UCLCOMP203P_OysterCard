package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;

import com.tfl.billing.ScanListener;

import java.math.BigDecimal;
import java.util.*;

public class TravelTracker implements ScanListener {

    private final List<JourneyEvent> eventLog = new ArrayList<JourneyEvent>();
    private final Set<UUID> currentlyTravelling = new HashSet<UUID>();

    private DatabaseAdapter database = DatabaseAdapter.getInstance();

    public void chargeAccounts() {
        CustomerDatabase customerDatabase = CustomerDatabase.getInstance();

        List<Customer> customers = customerDatabase.getCustomers();
        for (Customer customer : customers) {
            totalJourneysFor(customer);
        }
    }

    private void totalJourneysFor(Customer customer) {

        RecordJourneys record = new RecordJourneys(eventLog, customer);
        List<Journey> journeys = record.getJourneys();


        CostCalculator calculator = new CostCalculator(journeys);
        BigDecimal totalCostForToday = calculator.getCustomerTotal();

        // New Payments System Adapter
        PaymentsSystemAdapter paymentSystem = new PaymentsSystemAdapter(customer, journeys, totalCostForToday);
        paymentSystem.charge();
    }


    public void connect(OysterCardReader... cardReaders) {
        for (OysterCardReader cardReader : cardReaders) {
            cardReader.register(this);
        }
    }

    @Override
    public boolean cardScanned(UUID cardId, UUID readerId) {
        boolean cardScanSuccess = false;

        if (currentlyTravelling.contains(cardId)) {
            eventLog.add(new JourneyEnd(cardId, readerId));
            currentlyTravelling.remove(cardId);
            cardScanSuccess = true;
        }
        else {
            if (database.isRegisteredId(cardId)) {
                currentlyTravelling.add(cardId);
                eventLog.add(new JourneyStart(cardId, readerId));
                cardScanSuccess = true;
            } else {
                throw new UnknownOysterCardException(cardId);
            }
        }
        return cardScanSuccess;
    }
}
