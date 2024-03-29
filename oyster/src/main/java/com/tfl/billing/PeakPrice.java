package com.tfl.billing;

import java.math.BigDecimal;

public class PeakPrice implements Price {
    private final BigDecimal PEAK_LONG_JOURNEY_PRICE = new BigDecimal(3.80);
    private final BigDecimal PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(2.90);

    private final BigDecimal PEAK_DAILY_CAP = new BigDecimal(9.00);

    @Override
    public BigDecimal getDailyCap() { return PEAK_DAILY_CAP; }

    @Override
    public BigDecimal getLong() { return PEAK_LONG_JOURNEY_PRICE; }

    @Override
    public BigDecimal getShort() { return PEAK_SHORT_JOURNEY_PRICE; }

    public Double getDoubleCap() { return getDailyCap().doubleValue(); }
}
