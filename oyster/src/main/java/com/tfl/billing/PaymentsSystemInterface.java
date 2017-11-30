package com.tfl.billing;

import com.tfl.external.Customer;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PaymentsSystemInterface {
    void charge(Customer customer, List<Journey> journeys, BigDecimal totalBill);

    String stationWithReader(UUID originId);
}
