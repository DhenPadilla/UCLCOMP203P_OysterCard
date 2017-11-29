package com.tfl.billing;

import static org.mockito.Mockito.*;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;

import org.junit.Test;

import java.lang.reflect.Array;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class CostCalculatorTest {

   @Test
   public void checkForCorrectCalculationPeakSingleShort() {
      List<Journey> journeys = new ArrayList<Journey>();

      List<Customer> customers = CustomerDatabase.getInstance().getCustomers();
      HashMap<Integer, OysterCardReader> readers = new HashMap<Integer, OysterCardReader>();

      for(int i = 0 ; i < 2; i++) {
         OysterCardReader mockReader = mock(OysterCardReader.class);
         readers.put(i, mockReader);
      }

      for (int i = 0; i < 2; i += 2) {
         JourneyStart start = new JourneyStart(customers.get(i).cardId(), readers.get(i).id());
         JourneyEnd end = new JourneyEnd(customers.get(i).cardId(), readers.get((i + 1)).id());

         journeys.add(new Journey(start, end));
      }

      CostCalculator calculator = new CostCalculator(journeys);

      String totalPrice = calculator.getCustomerTotal().toString();
      String actualPrice = "2.90";

      assertEquals(totalPrice, actualPrice);
   }



   @Test
   public void checkForCorrectCalculationPeakCap() {
      List<Journey> journeys = new ArrayList<Journey>();

      List<Customer> customers = CustomerDatabase.getInstance().getCustomers();
      HashMap<Integer, OysterCardReader> readers = new HashMap<Integer, OysterCardReader>();

      for(int i = 0 ; i < 8; i++) {
         OysterCardReader mockReader = mock(OysterCardReader.class);
         readers.put(i, mockReader);
      }

      for (int i = 0; i < 8; i += 2) {
         JourneyStart start = new JourneyStart(customers.get(i).cardId(), readers.get(i).id());
         JourneyEnd end = new JourneyEnd(customers.get(i).cardId(), readers.get((i + 1)).id());

         journeys.add(new Journey(start, end));
      }

      CostCalculator calculator = new CostCalculator(journeys);

      String totalPrice = calculator.getCustomerTotal().toString();
      String actualPrice = "9.00";

      assertEquals(totalPrice, actualPrice);
   }
}