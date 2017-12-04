package com.tfl.billing;

import com.tfl.external.Customer;

import java.util.ArrayList;
import java.util.List;

public class RecordJourneys {

    private List<JourneyEvent> eventLog;
    private Customer customer;

    public RecordJourneys(List<JourneyEvent> eventLog, Customer customer) {
        this.eventLog = eventLog;
        this.customer = customer;
    }

    List<JourneyEvent> customerJourneyEvents = new ArrayList<JourneyEvent>();

    public List<Journey> getJourneys() {

        for (JourneyEvent journeyEvent : eventLog) {
            if (journeyEvent.cardId().equals(customer.cardId())) {
                customerJourneyEvents.add(journeyEvent);
            }
        }

        List<Journey> journeys = new ArrayList<Journey>();

        JourneyEvent start = null;
        for (JourneyEvent event : eventLog) {
            if (event instanceof JourneyStart) {
                start = event;
            }
            if (event instanceof JourneyEnd && start != null) {
                journeys.add(new Journey.JourneyBuilder(start, event).build());
            }
        }

        return journeys;
    }
}
