package com.tfl.billing;

import org.junit.Test;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

public class JourneyTest {
    private UUID cardID = UUID.randomUUID();
    private UUID readerID = UUID.randomUUID();
    //ClockInterface clock = new SystemClock();

    public static Date peakDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 2017-12-01);
        calendar.set(Calendar.MINUTE, 25);
        calendar.set(Calendar.HOUR, 17);
        return calendar.getTime();
    }

    private JourneyStart journeyStart = new JourneyStart(cardID, readerID);
    private JourneyEnd journeyEnd = new JourneyEnd(cardID, readerID);
    private Journey testJourney = new Journey.JourneyBuilder(journeyStart, journeyEnd).setStartTime(1).build();
    /*
    @Test
    public void originIdTest() {
        assertEquals(testJourney.originId(), journeyStart.readerId());
    }

    @Test
    public void destinationIdTest() {
        assertEquals(testJourney.destinationId(),(journeyEnd.readerId()));
    }

    @Test
    public void StartTimeTest() throws InterruptedException {
        assertTrue(testJourney.startTime().equals(new Date(journeyStart.time())));
    }

    @Test
    public void EndTimeTest() throws InterruptedException {
        assertTrue(testJourney.endTime().equals(new Date(journeyEnd.time())));
    }

    @Test
    public void JourneyDurationSecTest() throws InterruptedException {
        assertEquals(testJourney.durationSeconds(), (int) ((journeyEnd.time() - journeyStart.time()) / 1000));
    }

    @Test
    public void JourneyDurationMinTest() throws InterruptedException {
        assertEquals(testJourney.durationMinutes(), "" + testJourney.durationSeconds() / 60 + ":" + testJourney.durationSeconds() % 60);
    }

    @Test
    public void formattedStartTimeTest() throws InterruptedException {
        assertEquals(testJourney.formattedStartTime(),(SimpleDateFormat.getInstance().format(new Date(journeyStart.time()))));
    }

    @Test
    public void formattedEndTimeTest() throws InterruptedException {
        assertEquals(testJourney.formattedEndTime(),(SimpleDateFormat.getInstance().format(new Date(journeyEnd.time()))));
    }
    */

}