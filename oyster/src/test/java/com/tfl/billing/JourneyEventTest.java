package com.tfl.billing;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class JourneyEventTest {
    private UUID card = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    private UUID reader = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    private JourneyStart myJourney = new JourneyStart(card, reader);

    @Test
    public void cardId() throws Exception {
        assertEquals(card, myJourney.cardId());
    }

    @Test
    public void readerId() throws Exception {
        assertEquals(reader, myJourney.readerId());
    }

    @Test
    public void time() throws Exception {
        assertEquals(System.currentTimeMillis(), myJourney.time());
    }

}