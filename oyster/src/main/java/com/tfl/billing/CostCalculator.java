package com.tfl.billing;

import java.math.BigDecimal;
import java.util.*;

public class CostCalculator {

    //Journeys taken
    private List<Journey> journeysTaken;

    //PEAK PRICES
    PeakPrice peaks = new PeakPrice();
    //OFF PEAK PRICES
    OffPeakPrice offPeaks = new OffPeakPrice();


    public CostCalculator(List<Journey> journeys) {
        journeysTaken = journeys;
    }

    public BigDecimal getOldCustomerTotal() { //OLD PRICES
        BigDecimal customerTotal = new BigDecimal(0);
        for (Journey j: journeysTaken) {
            BigDecimal journeyPrice = offPeaks.getOldPrice();
            if (peak(j)) { journeyPrice = peaks.getOldPrice(); }
            customerTotal = customerTotal.add(journeyPrice);
        }
        return customerTotal;
    }


    public BigDecimal getCustomerTotal() { //NEW PRICES
        BigDecimal customerTotal = new BigDecimal(0);
        boolean peak = false;
        for (Journey j : journeysTaken){
            BigDecimal journeyPrice = offPeaks.getShort();
            // Get duration of journey
            String journeyDurationStr = j.durationMinutes();
            int journeyDuration = Integer.parseInt(journeyDurationStr.substring(0, journeyDurationStr.indexOf(":")));
            // Calculate costs
            if (peak(j)) {
                if (journeyDuration < 25) {
                    journeyPrice = peaks.getShort();
                    peak = true;
                }
                else {
                    journeyPrice = peaks.getLong();
                }
            }
            else if (journeyDuration > 25) journeyPrice = offPeaks.getLong();
            customerTotal = customerTotal.add(journeyPrice);
        }

        // REQ: Daily Caps
        if (peak && (peaks.getDoubleCap() - customerTotal.doubleValue()) < 1) customerTotal = peaks.getDailyCap();
        else if (!peak && (offPeaks.getDoubleCap() - customerTotal.doubleValue()) < 1) customerTotal = offPeaks.getDailyCap();

        //customerTotal = customerTotal.add(customerTotal);
        return roundToNearestPenny(customerTotal);
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
}
