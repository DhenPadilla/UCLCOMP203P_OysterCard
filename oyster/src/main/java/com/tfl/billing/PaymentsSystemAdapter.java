package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.List;

public class PaymentsSystemAdapter implements PaymentsSystemInterface {
    private Customer customer;
    private List<Journey> journeys;
    private BigDecimal totalBill;

    public PaymentsSystemAdapter(Customer customer, List<Journey> journeys, BigDecimal totalBill) {
        this.customer = customer;
        this.journeys = journeys;
        this.totalBill = totalBill;
    }

    @Override
    public void charge() {
        if(customer.cardId() != null) {
            PaymentsSystem.getInstance().charge(customer, journeys, totalBill);
        }
    }
}
