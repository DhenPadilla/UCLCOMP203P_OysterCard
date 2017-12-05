package com.tfl.billing;

import static org.mockito.Mockito.*;

import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CostCalculatorTest {

   private List<Journey> addJourneys(boolean peak, boolean longDuration, boolean cap, OysterCardReader mockReader) {
      List<Journey> journeys = new ArrayList<Journey>();

      List<Customer> customers = CustomerDatabase.getInstance().getCustomers();

      HashMap<Integer, OysterCardReader> readers = addReaders(mockReader, cap);

      if(!cap) {
         for (int i = 0; i < 2; i += 2) {
            JourneyStart start = new JourneyStart(customers.get(i).cardId(), readers.get(i).getReaderId());
            JourneyEnd end = new JourneyEnd(customers.get(i).cardId(), readers.get((i + 1)).getReaderId());
            if (peak && longDuration) {
               journeys.add(new Journey.JourneyBuilder(start, end).setStartTime(18).setEndTime(18, 30).build());
            } else if (peak && !longDuration) {
               journeys.add(new Journey.JourneyBuilder(start, end).setStartTime(18).setEndTime(18, 5).build());
            } else if (!peak && longDuration) {
               journeys.add(new Journey.JourneyBuilder(start, end).setStartTime(13).setEndTime(13, 30).build());
            } else {
               journeys.add(new Journey.JourneyBuilder(start, end).setStartTime(13).setEndTime(13, 5).build());
            }
         }
      }
      else {
         for (int i = 0; i < 10; i += 2) {
            JourneyStart start = new JourneyStart(customers.get(i).cardId(), readers.get(i).getReaderId());
            JourneyEnd end = new JourneyEnd(customers.get(i).cardId(), readers.get((i + 1)).getReaderId());
            if (peak) {
               journeys.add(new Journey.JourneyBuilder(start, end).setStartTime(18).setEndTime(18, 10).build());
            }
            else if (!peak) {
               journeys.add(new Journey.JourneyBuilder(start, end).setStartTime(13).setEndTime(13, 10).build());
            }
         }
      }

      return journeys;
   }

   private HashMap<Integer, OysterCardReader> addReaders(OysterCardReader mockReader, boolean cap) {
      HashMap<Integer, OysterCardReader> readers = new HashMap<Integer, OysterCardReader>();

      when(mockReader.getReaderId()).thenReturn(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));

      if(!cap) {
         for (int i = 0; i < 2; i++) {
            readers.put(i, mockReader);
         }
      }
      else {
         for (int i = 0; i < 20; i++) {
            readers.put(i, mockReader);
         }
      }
      return readers;
   }

   @Test
   public void checkForCorrectCalculationSinglePeakShort() {
      OysterCardReader mockReader = mock(OysterCardReader.class);
      List<Journey> journeys = addJourneys(true, false, false, mockReader);

      CostCalculator calculator = new CostCalculator(journeys);

      double totalPrice = calculator.getCustomerTotal().doubleValue();
      double actualPrice = 2.90;

      assertEquals(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), mockReader.getReaderId());
      assertEquals(totalPrice, actualPrice, 0);
   }

   @Test
   public void checkForCorrectCalculationSinglePeakLong() {
      OysterCardReader mockReader = mock(OysterCardReader.class);
      List<Journey> journeys = addJourneys(true, true, false, mockReader);

      CostCalculator calculator = new CostCalculator(journeys);

      double totalPrice = calculator.getCustomerTotal().doubleValue();
      double actualPrice = 3.80;

      assertEquals(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), mockReader.getReaderId());
      assertEquals(totalPrice, actualPrice, 0);
   }

   @Test
   public void checkForCorrectCalculationSingleOffPeakShort() {
      OysterCardReader mockReader = mock(OysterCardReader.class);
      List<Journey> journeys = addJourneys(false, false,false, mockReader);

      CostCalculator calculator = new CostCalculator(journeys);

      double totalPrice = calculator.getCustomerTotal().doubleValue();
      double actualPrice = 1.60;

      assertEquals(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), mockReader.getReaderId());
      assertEquals(totalPrice, actualPrice, 0);
   }

   @Test
   public void checkForCorrectCalculationSingleOffPeakLong() {
      OysterCardReader mockReader = mock(OysterCardReader.class);
      List<Journey> journeys = addJourneys(false, true, false, mockReader);

      CostCalculator calculator = new CostCalculator(journeys);

      double totalPrice = calculator.getCustomerTotal().doubleValue();
      double actualPrice = 2.70;

      assertEquals(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), mockReader.getReaderId());
      assertEquals(totalPrice, actualPrice, 0);
   }

   @Test
   public void checkForCorrectCalculationPeakCap() {
      OysterCardReader mockReader = mock(OysterCardReader.class);
      List<Journey> journeys = addJourneys(true, true, true, mockReader);

      CostCalculator calculator = new CostCalculator(journeys);

      double totalPrice = calculator.getCustomerTotal().doubleValue();
      double actualPrice = 9.00;

      assertEquals(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), mockReader.getReaderId());
      assertEquals(totalPrice, actualPrice, 0);
   }


   @Test
   public void checkForCorrectCalculationOffPeakCap() {
      OysterCardReader mockReader = mock(OysterCardReader.class);
      List<Journey> journeys = addJourneys(false, true, true, mockReader);

      CostCalculator calculator = new CostCalculator(journeys);

      double totalPrice = calculator.getCustomerTotal().doubleValue();
      double actualPrice = 7.00;

      assertEquals(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), mockReader.getReaderId());
      assertEquals(totalPrice, actualPrice, 0);
   }
}