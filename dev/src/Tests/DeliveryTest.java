package Tests;

import BussinessLayer.DeliveryPackage.Delivery;
import BussinessLayer.DeliveryPackage.DeliveryFacade;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import java.util.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeliveryTest {

    private DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();

    @BeforeEach
    public void setUp() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2030");
        Time newTime = Time.valueOf("12:30:00");
        Map<String, Integer> items1 = new HashMap<String, Integer>() {
            {
                put("milk", 20);
                put("pasta", 50);
                put("chocolate", 10);
                put("cola", 10);
            }
        };
        Map<String, Integer> items3 = new HashMap<String, Integer>() {
            {
                put("eggs", 10);
                put("cola zero", 15);
                put("beer", 23);
                put("candy", 17);
            }
        };
        List<String> centerLocations = new ArrayList<String>() {
            {
                add("1");
                add("3");
            }
        };
        List<String> orders1 = new ArrayList<String>() {
            {
                add("12");
                add("56");
            }
        };
        deliveryFacade.createDriver("208938985", "omri", "A", date);
        deliveryFacade.createLocation("1", "superli", "lachish 151 shoham", "0543160553", "yossi", "Center");
        deliveryFacade.createLocation("3", "shufersal", "azrieli tel aviv", "0543160550", "ronit", "Center");
        deliveryFacade.createLocation("4", "tara", "bialik 32 ramat gan", "0581234567", "moshe", "Center");
        deliveryFacade.createTruck("2360154", "volvo", 1000.0, 4500.0);
        deliveryFacade.createOrder("12", items1, "487", "1", 1000.0);
        deliveryFacade.createOrder("56", items3, "263", "3", 2500.0);
        deliveryFacade.createDelivery("101", date, newTime, "208938985", "4", centerLocations, "2360154", orders1);
    }

    public void updateForNextTest() throws Exception
    {
        if(deliveryFacade.getDrivers().containsKey("208938985"))
            deliveryFacade.removeDriver("208938985");
        if(deliveryFacade.getLocations().containsKey("1"))
            deliveryFacade.removeLocation("1");
        if(deliveryFacade.getLocations().containsKey("3"))
            deliveryFacade.removeLocation("3");
        if(deliveryFacade.getLocations().containsKey("4"))
            deliveryFacade.removeLocation("4");
        if(deliveryFacade.getTrucks().containsKey("2360154"))
            deliveryFacade.removeTruck("2360154");
        if(deliveryFacade.getOrders().containsKey("12"))
            deliveryFacade.removeOrder("12");
        if(deliveryFacade.getOrders().containsKey("56"))
            deliveryFacade.removeOrder("56");
        if(deliveryFacade.getDeliveries().containsKey("101"))
            deliveryFacade.removeDelivery("101");
    }

    @Test
    public void testCreateObjects() throws Exception {
        this.setUp();
        assertTrue(deliveryFacade.getDriver("208938985").getName().equals("omri"));
        assertTrue(deliveryFacade.getDriver("208938985").getLicenseType().equals("A"));
        assertTrue(deliveryFacade.getLocation("1").getName().equals("superli"));
        assertTrue(deliveryFacade.getLocation("1").getAddress().equals("lachish 151 shoham"));
        assertTrue(deliveryFacade.getLocation("1").getContactName().equals("yossi"));
        assertTrue(deliveryFacade.getLocation("1").getShippingArea().equals("Center"));
        assertTrue(deliveryFacade.getTruck("2360154").getModel().equals("volvo"));
        assertEquals(1000.0, deliveryFacade.getTruck("2360154").getNetoWeight(), 0.0);
        assertEquals(4500.0, deliveryFacade.getTruck("2360154").getTotalWeight(), 0.0);
        assertTrue(deliveryFacade.getOrders().get("12").getLocationId().equals("1"));
        int amount = deliveryFacade.getOrders().get("12").getItems().get("milk");
        assertEquals(20, amount, 0);
        assertEquals(1000.0, deliveryFacade.getOrders().get("12").getWeight(), 0.0);
        assertTrue(deliveryFacade.getDelivery("101").getDriverId().equals("208938985"));
        assertTrue(deliveryFacade.getDelivery("101").getTruckId().equals("2360154"));
        assertTrue(deliveryFacade.getDelivery("101").getSrcLocation().equals("4"));
        assertTrue(deliveryFacade.getDelivery("101").getTargets().get(0).equals("1"));
        assertTrue(deliveryFacade.getDelivery("101").getOrders().get(0).equals("12"));
        this.updateForNextTest();
    }


    @Test
    public void testRemoveObjectcs() throws Exception {
        this.setUp();
        deliveryFacade.removeDriver("208938985");
        deliveryFacade.removeTruck("2360154");
        deliveryFacade.removeLocation("1");
        deliveryFacade.removeOrder("12");
        deliveryFacade.removeDelivery("101");
        assertTrue(!deliveryFacade.getDrivers().containsKey("208938985"));
        assertTrue(!deliveryFacade.getTrucks().containsKey("2360154"));
        assertTrue(!deliveryFacade.getLocations().containsKey("1"));
        assertTrue(!deliveryFacade.getOrders().containsKey("12"));
        assertTrue(!deliveryFacade.getDeliveries().containsKey("101"));
        this.updateForNextTest();
    }

    @Test
    public void testOrderChanges() throws Exception {
        this.setUp();
        deliveryFacade.addItem("12", "bamba", 5);
        int amount = deliveryFacade.getOrders().get("12").getItems().get("bamba");
        assertEquals(5, amount, 0);
        deliveryFacade.updateQuantity("12", "bamba", 10);
        amount = deliveryFacade.getOrders().get("12").getItems().get("bamba");
        assertEquals(10, amount, 0);
        deliveryFacade.removeItem("12", "bamba");
        assertTrue(!deliveryFacade.getOrders().get("12").getItems().values().contains("bamba"));
        this.updateForNextTest();
    }

    @Test
    public void testTruckDetails() throws Exception {
        this.setUp();
        deliveryFacade.getTruck("2360154").setUsed();
        assertTrue(deliveryFacade.getTruck("2360154").isUsed());
        deliveryFacade.getTruck("2360154").setNotUsed();
        assertTrue(!deliveryFacade.getTruck("2360154").isUsed());
        this.updateForNextTest();
    }

    @Test
    public void testDriverDetails() throws Exception {
        this.setUp();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2030");
        deliveryFacade.getDriver("208938985").setDriving();
        assertTrue(deliveryFacade.getDriver("208938985").isBusy());
        deliveryFacade.getDriver("208938985").setNotDriving();
        assertTrue(!deliveryFacade.getDriver("208938985").isBusy());
        deliveryFacade.getDriver("208938985").setLicenseType("B");
        assertTrue(deliveryFacade.getDriver("208938985").getLicenseType().equals("B"));
        deliveryFacade.getDriver("208938985").setExpLicenseDate(date);
        assertTrue(deliveryFacade.getDriver("208938985").getExpLicenseDate().equals(date));
        this.updateForNextTest();
    }

    @Test
    public void testLocationDetails() throws Exception {
        this.setUp();
        deliveryFacade.getLocation("1").setTelNumber("0521234567");
        assertTrue(deliveryFacade.getLocation("1").getTelNumber().equals("0521234567"));
        deliveryFacade.getLocation("1").setContactName("roi");
        assertTrue(deliveryFacade.getLocation("1").getContactName().equals("roi"));
        this.updateForNextTest();
    }

    @Test
    public void testDeliveryDetails() throws Exception {
        this.setUp();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2030");
        Time newTime = Time.valueOf("14:30:00");
        deliveryFacade.createDriver("312164668", "noa", "B", date);
        deliveryFacade.createTruck("8523123", "chevrolet", 5000.0, 9000.5);
        deliveryFacade.getDelivery("101").setDeliveryDate(date);
        assertTrue(deliveryFacade.getDelivery("101").getDeliveryDate().equals(date));
        deliveryFacade.getDelivery("101").setLeavingTime(newTime);
        assertTrue(deliveryFacade.getDelivery("101").getLeavingTime().equals(newTime));
        deliveryFacade.getDelivery("101").setWeight(100);
        assertEquals(100, deliveryFacade.getDelivery("101").getWeight(), 0);
        deliveryFacade.getDelivery("101").setTruckId("8523123");
        assertTrue(deliveryFacade.getDelivery("101").getTruckId().equals("8523123"));
        deliveryFacade.getDelivery("101").setDriverId("312164668");
        assertTrue(deliveryFacade.getDelivery("101").getDriverId().equals("312164668"));
        this.updateForNextTest();
    }

    @Test
    public void testFullWorkFlowOrder() throws Exception {
        this.setUp();
        Map<String, Integer> items2 = new HashMap<String, Integer>() {
            {
                put("milk", 25);
                put("rice", 30);
                put("cheese", 40);
                put("eggs", 45);
            }
        };
        deliveryFacade.createOrder("34", items2, "159", "2", 3500.0);
        deliveryFacade.addItem("34", "bamba", 5);
        deliveryFacade.addItem("34", "bisli", 10);
        int amount = deliveryFacade.getOrders().get("34").getItems().get("bamba");
        assertEquals(amount, 5);
        deliveryFacade.updateQuantity("34", "bamba", 20);
        amount = deliveryFacade.getOrders().get("34").getItems().get("bamba");
        assertEquals(amount, 20);
        deliveryFacade.removeItem("34", "bamba");
        assertTrue(deliveryFacade.getOrders().containsKey("34"));
        deliveryFacade.removeItem("34", "bisli");
        assertTrue(!deliveryFacade.getOrders().get("34").getItems().values().contains("bamba"));
        assertTrue(!deliveryFacade.getOrders().get("34").getItems().values().contains("bisli"));
        this.updateForNextTest();
    }

    @Test
    public void testFullWorkFlowDelivery() throws Exception {
        this.setUp();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2030");
        Time newTime = Time.valueOf("14:30:00");
        deliveryFacade.createDriver("123456789", "lidor", "C", date);
        deliveryFacade.createLocation("2", "maxstock", "shoham 26 haifa", "0504616909", "ben", "North");
        deliveryFacade.createLocation("5", "tnuva", "rabin 12 beer sheva", "0538523644", "assaf", "North");
        deliveryFacade.createLocation("6", "osem", "shimshon 24 krayot", "0528549847", "shoshana", "South");
        deliveryFacade.createTruck("30122623", "chevrolet", 5000.0, 9000.5);
        Map<String, Integer> items4 = new HashMap<String, Integer>() {
            {
                put("eggs", 10);
                put("milk", 15);
                put("tomato", 23);
                put("cucumber", 17);
            }
        };
        Map<String, Integer> items5 = new HashMap<String, Integer>() {
            {
                put("ice cream", 20);
                put("toilet paper", 15);
                put("cucumber", 50);
                put("fish", 10);
            }
        };
        deliveryFacade.createOrder("78", items4, "546", "2", 2000.0);
        deliveryFacade.createOrder("98", items5, "943", "5", 2000.0);
        List<String> orders3 = new ArrayList<String>() {
            {
                add("78");
                add("98");
            }
        };
        List<String> locations = new ArrayList<String>() {
            {
                add("2");
                add("5");
            }
        };
        deliveryFacade.createDelivery("103", date, newTime, "123456789", "6", locations, "30122623", orders3);
        deliveryFacade.setDriverToDrive("123456789");
        assertTrue(deliveryFacade.getDriver("123456789").isBusy());
        deliveryFacade.setTruckUsed("30122623");
        assertTrue(deliveryFacade.getTruck("30122623").isUsed());
        deliveryFacade.updateStatus("103", "InProgress");
        assertTrue(deliveryFacade.getDelivery("103").getStatus().equals(Delivery.Status.InProgress));
        deliveryFacade.updateStatus("103", "Done");
        assertTrue(deliveryFacade.getDelivery("103").getStatus().equals(Delivery.Status.Done));
        deliveryFacade.setDriverNotToDrive("123456789");
        assertTrue(!deliveryFacade.getDriver("123456789").isBusy());
        deliveryFacade.setTruckNotUsed("30122623");
        assertTrue(!deliveryFacade.getTruck("30122623").isUsed());
        this.updateForNextTest();
    }

    @Test
    public void testFullWorkFlowDelivery2() throws Exception {
        this.setUp();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2030");
        Time newTime = Time.valueOf("14:30:00");
        deliveryFacade.createDriver("852963741", "lidor", "C", date);
        deliveryFacade.createLocation("7", "maxstock", "shoham 26 haifa", "0504616909", "ben", "North");
        deliveryFacade.createLocation("8", "tnuva", "rabin 12 beer sheva", "0538523644", "assaf", "North");
        deliveryFacade.createLocation("9", "osem", "shimshon 24 krayot", "0528549847", "shoshana", "South");
        deliveryFacade.createTruck("7896541", "chevrolet", 5000.0, 9000.5);
        Map<String, Integer> items2 = new HashMap<String, Integer>() {
            {
                put("milk", 25);
                put("rice", 30);
                put("cheese", 40);
                put("eggs", 45);
            }
        };
        Map<String, Integer> items3 = new HashMap<String, Integer>() {
            {
                put("eggs", 10);
                put("cola zero", 15);
                put("beer", 23);
                put("candy", 17);
            }
        };
        deliveryFacade.createOrder("50", items2, "546", "2", 2000.0);
        deliveryFacade.createOrder("60", items3, "943", "5", 2000.0);
        List<String> orders3 = new ArrayList<String>() {
            {
                add("50");
                add("60");
            }
        };
        List<String> locations = new ArrayList<String>() {
            {
                add("7");
                add("8");
            }
        };
        deliveryFacade.createDelivery("105", date, newTime, "852963741", "9", locations, "7896541", orders3);
        deliveryFacade.setDriverToDrive("852963741");
        assertTrue(deliveryFacade.getDriver("852963741").isBusy());
        deliveryFacade.setTruckUsed("7896541");
        assertTrue(deliveryFacade.getTruck("7896541").isUsed());
        deliveryFacade.updateStatus("105", "InProgress");
        assertTrue(deliveryFacade.getDelivery("105").getStatus().equals(Delivery.Status.InProgress));
        deliveryFacade.updateStatus("105", "Done");
        assertTrue(deliveryFacade.getDelivery("105").getStatus().equals(Delivery.Status.Done));
        deliveryFacade.setDriverNotToDrive("852963741");
        assertTrue(!deliveryFacade.getDriver("852963741").isBusy());
        deliveryFacade.setTruckNotUsed("7896541");
        assertTrue(!deliveryFacade.getTruck("7896541").isUsed());
        deliveryFacade.createDriver("741852963", "lidor", "C", date);
        deliveryFacade.createTruck("846315", "volvo", 1000.0, 10800.0);
        Assertions.assertThrows(Exception.class, () -> {
            deliveryFacade.createDelivery("108", date, newTime, "208938985", "9", locations, "846315", orders3);
        });
        Assertions.assertThrows(Exception.class, () -> {
            deliveryFacade.createDelivery("107", date, newTime, "741852963", "9", locations, "7896541", orders3);
        });
        this.updateForNextTest();
    }
}
