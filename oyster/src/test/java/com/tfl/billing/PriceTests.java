package com.tfl.billing;

import org.junit.Test;

import static org.junit.Assert.*;

public class PriceTests {

    @Test
    public void peakPriceShortTest() {
        PeakPrice peaks = new PeakPrice();

        double actual = peaks.getShort().doubleValue();
        double expected = 2.90;

        assertEquals(expected, actual, 0);
    }

    @Test
    public void peakPriceLongTest() {
        PeakPrice peaks = new PeakPrice();

        double actual = peaks.getLong().doubleValue();
        double expected = 3.80;

        assertEquals(expected, actual, 0);
    }

    @Test
    public void peakCapTest() {
        PeakPrice peaks = new PeakPrice();

        double actual = peaks.getDoubleCap();
        double expected = 9.00;

        assertEquals(expected, actual, 0);
    }

    @Test
    public void offPeakPriceShortTest() {
        OffPeakPrice peaks = new OffPeakPrice();

        double actual = peaks.getShort().doubleValue();
        double expected = 1.60;

        assertEquals(expected, actual, 0);
    }

    @Test
    public void offPeakPriceLongTest() {
        OffPeakPrice peaks = new OffPeakPrice();

        double actual = peaks.getLong().doubleValue();
        double expected = 2.70;

        assertEquals(expected, actual, 0);
    }

    @Test
    public void offPeakCapTest() {
        OffPeakPrice peaks = new OffPeakPrice();

        double actual = peaks.getDoubleCap();
        double expected = 7.00;

        assertEquals(expected, actual, 0);
    }
}
