package com.tfl.billing;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

public class CostCalculator {

    //Acts as a Model Class

    //Journeys taken
    private List<Journey> journeysTaken;

    //PRICES - OLD
    static final BigDecimal OFF_PEAK_JOURNEY_PRICE = new BigDecimal(2.40);
    static final BigDecimal PEAK_JOURNEY_PRICE = new BigDecimal(3.20);

    // NEW: Long and Short journey prices or Peak and Off Peak
    static final BigDecimal OFF_PEAK_LONG_JOURNEY_PRICE = new BigDecimal(2.70);
    static final BigDecimal OFF_PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(1.60);
    static final BigDecimal PEAK_LONG_JOURNEY_PRICE = new BigDecimal(3.80);
    static final BigDecimal PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(2.90);

    // NEW: Daily Cap prices for Peak and Off Peak Journeys
    static final BigDecimal OFF_PEAK_DAILY_CAP = new BigDecimal(7.00);
    static final BigDecimal PEAK_DAILY_CAP = new BigDecimal(9.00);


    public CostCalculator(List<Journey> journeys) {
        journeysTaken = journeys;
    }


    public BigDecimal getCustomerTotal() {
        // OLD: Normal Peak & Off Peak times - still in use, though
        BigDecimal customerTotal = new BigDecimal(0);

        // NEW: Long and Short journey prices
        BigDecimal newCustomerTotal = new BigDecimal(0);

        /*
        OLD: Decide whether we keep this.
        for (Journey j: journeysTaken) {
            BigDecimal journeyPrice = OFF_PEAK_JOURNEY_PRICE;

            if (peak(j)) {
                journeyPrice = PEAK_JOURNEY_PRICE;
            }

            customerTotal = customerTotal.add(journeyPrice);
        }
        */

        int totPeakTrips = 0;

        for (Journey j : journeysTaken){
            BigDecimal journeyPrice = OFF_PEAK_SHORT_JOURNEY_PRICE;

            // Get duration of journey
            String journeyDurationStr = j.durationMinutes();
            int journeyDuration = Integer.parseInt(journeyDurationStr.substring(0, journeyDurationStr.indexOf(":")));

            // Calculate costs
            if (peak(j)) {
                if (journeyDuration < 25) {
                    journeyPrice = PEAK_SHORT_JOURNEY_PRICE;
                    totPeakTrips++;
                }

                else {
                    journeyPrice = PEAK_LONG_JOURNEY_PRICE;
                }
            }

            else if (journeyDuration > 25) journeyPrice = OFF_PEAK_LONG_JOURNEY_PRICE;
            newCustomerTotal = newCustomerTotal.add(journeyPrice);
        }


        // REQ: Daily Caps
        if (totPeakTrips > 0 && (PEAK_DAILY_CAP.doubleValue() - newCustomerTotal.doubleValue()) < 1) newCustomerTotal = PEAK_DAILY_CAP;
        else if (totPeakTrips == 0 && (OFF_PEAK_DAILY_CAP.doubleValue() - newCustomerTotal.doubleValue()) < 1) newCustomerTotal = OFF_PEAK_DAILY_CAP;

        customerTotal = customerTotal.add(newCustomerTotal);
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
