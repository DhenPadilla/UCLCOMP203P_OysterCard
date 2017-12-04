package com.tfl.billing;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class JourneyEventTest {
    private UUID card = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    private UUID reader = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    private ClockInterface clock = new SystemClock();

    private JourneyStart journeyStart = new JourneyStart(card, reader, clock);
    private JourneyEnd journeyEnd = new JourneyEnd(card, reader, clock);

    @Test
    public void checkCardReaderWorksForUserID() {
        assertEquals(card, journeyStart.cardId());
    }

    @Test
    public void checkCardReaderWorksForReaderID() {
        assertEquals(reader, journeyStart.readerId());
    }

    @Test
    public void checkCardReaderClosesForUID() {
        assertEquals(card, journeyEnd.cardId());
    }

    @Test
    public void checkCardReaderClosesForReaderID() {
        assertEquals(reader, journeyEnd.readerId());
    }

}