package com.tfl.billing;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class JourneyEventTest {
    private UUID card = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    private UUID reader = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    private JourneyStart myJourney = new JourneyStart(card, reader);
    private JourneyEnd myJourneyEnd = new JourneyEnd(card, reader);

    @Test
    public void checkIfTheCardReaderWorksForUserID() {
        assertEquals(card, myJourney.cardId());
    }

    @Test
    public void checkIfTheCardReaderWorksForReaderID() {
        assertEquals(reader, myJourney.readerId());
    }

    @Test
    public void checkIfTheCardReaderClosesForUID() {
        assertEquals(card, myJourneyEnd.cardId());
    }

    @Test
    public void checkIfTheCardReaderClosesForReaderID() {
        assertEquals(reader, myJourneyEnd.readerId());
    }

    /*
    @Test
    public void checkIfItCalculatesTimeCorrectly() throws Exception {
        assertEquals(System.currentTimeMillis(), myJourney.time());
    }
    */

}