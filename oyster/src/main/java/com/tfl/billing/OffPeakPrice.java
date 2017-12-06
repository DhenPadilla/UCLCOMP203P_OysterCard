package com.tfl.billing;

import java.math.BigDecimal;

public class OffPeakPrice implements Price {
    private final BigDecimal OFF_PEAK_LONG_JOURNEY_PRICE = new BigDecimal(2.70);
    private final BigDecimal OFF_PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(1.60);

    private final BigDecimal OFF_PEAK_DAILY_CAP = new BigDecimal(7.00);


    @Override
    public BigDecimal getDailyCap() {
        return OFF_PEAK_DAILY_CAP;
    }

    @Override
    public BigDecimal getLong() {
        return OFF_PEAK_LONG_JOURNEY_PRICE;
    }

    @Override
    public BigDecimal getShort() {
        return OFF_PEAK_SHORT_JOURNEY_PRICE;
    }

    public Double getDoubleCap() { return getDailyCap().doubleValue(); }
}
