package com.tfl.billing;

import static org.mockito.Mockito.*;

import com.oyster.OysterCard;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;

import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class CostCalculatorTest {

   TravelTracker tracker = new TravelTracker();

   //@Rule
   OysterCardReader mockedReader = mock(OysterCardReader.class);
   List<Customer> customers = CustomerDatabase.getInstance().getCustomers();

   @Test
   public void correctListOfReaderScans() {
       UUID readerId = mockedReader.getReaderId();
       tracker.cardScanned(customers.get(0).cardId(), readerId);

   }

   /*@Test
   public void generatesCorrectTotal() {
       Customer cust1 = new Customer("John Smith", new OysterCard());
       tracker.connect();
       tracker.cardScanned(cust1.cardId(), mockedReader.getReaderId());
       //assertEquals(cust1.cardId(), tracker.);
   }*/

}