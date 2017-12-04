package com.tfl.billing;

import static org.mockito.Mockito.*;

import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CostCalculatorTest {

   @Test
   public void checkForCorrectCalculationSinglePeakShort() {
      List<Journey> journeys = new ArrayList<Journey>();

      List<Customer> customers = CustomerDatabase.getInstance().getCustomers();
      HashMap<Integer, OysterCardReader> readers = new HashMap<Integer, OysterCardReader>();

      OysterCardReader mockReader = mock(OysterCardReader.class);
      when(mockReader.getReaderId()).thenReturn(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));

      for(int i = 0 ; i < 2; i++) {
         readers.put(i, mockReader);
      }

      for (int i = 0; i < 2; i += 2) {
         JourneyStart start = new JourneyStart(customers.get(i).cardId(), readers.get(i).getReaderId());
         JourneyEnd end = new JourneyEnd(customers.get(i).cardId(), readers.get((i + 1)).getReaderId());
         journeys.add(new Journey.JourneyBuilder(start, end).setStartTime(18).setEndTime(18, 5).build());
      }

      CostCalculator calculator = new CostCalculator(journeys);

      String totalPrice = calculator.getCustomerTotal().toString();
      String actualPrice = "2.90";

      assertEquals(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), mockReader.getReaderId());
      assertEquals(actualPrice, totalPrice);
   }


   @Test
   public void checkForCorrectCalculationSingleOffPeakShort() {
      List<Journey> journeys = new ArrayList<Journey>();

      List<Customer> customers = CustomerDatabase.getInstance().getCustomers();
      HashMap<Integer, OysterCardReader> readers = new HashMap<Integer, OysterCardReader>();

      for(int i = 0 ; i < 2; i++) {
         OysterCardReader mockReader = mock(OysterCardReader.class);
         readers.put(i, mockReader);
         when(mockReader.getReaderId()).thenReturn(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));
      }

      for (int i = 0; i < 2; i += 2) {
         JourneyStart start = new JourneyStart(customers.get(i).cardId(), readers.get(i).getReaderId());
         JourneyEnd end = new JourneyEnd(customers.get(i).cardId(), readers.get((i + 1)).getReaderId());

         journeys.add(new Journey.JourneyBuilder(start, end).setStartTime(13).setEndTime(13, 5).build());
      }

      CostCalculator calculator = new CostCalculator(journeys);

      String totalPrice = calculator.getCustomerTotal().toString();
      String actualPrice = "1.60";

      assertEquals(totalPrice, actualPrice);
   }


   @Test
   public void checkForCorrectCalculationPeakCap() {
      List<Journey> journeys = new ArrayList<Journey>();

      List<Customer> customers = CustomerDatabase.getInstance().getCustomers();
      HashMap<Integer, OysterCardReader> readers = new HashMap<Integer, OysterCardReader>();

      for(int i = 0 ; i < 10; i++) {
         OysterCardReader mockReader = mock(OysterCardReader.class);
         readers.put(i, mockReader);
         when(mockReader.getReaderId()).thenReturn(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));
      }

      for (int i = 0; i < 10; i += 2) {
         JourneyStart start = new JourneyStart(customers.get(i).cardId(), readers.get(i).getReaderId());
         JourneyEnd end = new JourneyEnd(customers.get(i).cardId(), readers.get((i + 1)).getReaderId());

         journeys.add(new Journey.JourneyBuilder(start, end).setStartTime(18).setEndTime(16, 5).build());
      }

      CostCalculator calculator = new CostCalculator(journeys);

      String totalPrice = calculator.getCustomerTotal().toString();
      String expectedPrice = "9.00";

      assertEquals(expectedPrice, totalPrice);
   }


   @Test
   public void checkForCorrectCalculationOffPeakCap() {
      List<Journey> journeys = new ArrayList<Journey>();

      List<Customer> customers = CustomerDatabase.getInstance().getCustomers();
      HashMap<Integer, OysterCardReader> readers = new HashMap<Integer, OysterCardReader>();

      for(int i = 0 ; i < 10; i++) {
         OysterCardReader mockReader = mock(OysterCardReader.class);
         readers.put(i, mockReader);
         when(mockReader.getReaderId()).thenReturn(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));
      }

      for (int i = 0; i < 10; i += 2) {
         JourneyStart start = new JourneyStart(customers.get(i).cardId(), readers.get(i).getReaderId());
         JourneyEnd end = new JourneyEnd(customers.get(i).cardId(), readers.get((i + 1)).getReaderId());

         journeys.add(new Journey.JourneyBuilder(start, end).setStartTime(13).setEndTime(13, 5).build());
      }

      CostCalculator calculator = new CostCalculator(journeys);

      String totalPrice = calculator.getCustomerTotal().toString();
      String expectedPrice = "7.00";

      assertEquals(expectedPrice, totalPrice);
   }

}