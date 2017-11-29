package com.tfl.billing;

import org.junit.Test;


import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

public class JourneyTest {
    private UUID cardID = UUID.randomUUID();
    private UUID readerID = UUID.randomUUID();
    private UUID destinationID = UUID.randomUUID();
    private JourneyStart journeyStart;
    private JourneyEnd journeyEnd;
    private Journey journey;


    @Test
    public void StartTimeTest() throws InterruptedException {
        journeyStart = new JourneyStart(cardID, readerID);
        journeyEnd = new JourneyEnd(cardID, readerID);
        Journey testJourney = new Journey(journeyStart, journeyEnd);

        assertTrue(testJourney.startTime().equals(new Date(journeyStart.time())));

    }

    @Test
    public void EndTimeTest() throws InterruptedException {
        journeyStart = new JourneyStart(cardID, readerID);
        journeyEnd = new JourneyEnd(cardID, readerID);
        Journey testJourney = new Journey(journeyStart, journeyEnd);

        assertTrue(testJourney.endTime().equals(new Date(journeyEnd.time())));
    }

    @Test
    public void JourneyDurationSecTest() throws InterruptedException {

    }

    @Test
    public void JourneyDurationMinTest() throws InterruptedException {

    }

    @Test
    public void formattedTimeTest() throws InterruptedException {

    }


}