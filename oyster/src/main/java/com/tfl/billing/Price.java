package com.tfl.billing;

import java.math.BigDecimal;

public interface Price {
   BigDecimal getDailyCap();
   BigDecimal getLong();
   BigDecimal getShort();
}
