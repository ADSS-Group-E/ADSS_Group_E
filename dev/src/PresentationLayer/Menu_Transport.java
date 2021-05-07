package PresentationLayer;

import BussinessLayer.DeliveryPackage.DeliveryFacade;
import BussinessLayer.Facade;
import BussinessLayer.Response;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

public class Menu_Transport {

    private static final Facade facade=new Facade();


    public static void createSystem(){
        Scanner in = new Scanner(System.in);

        try
        {
            System.out.println("Welcome to the Delivery System Manager");
            System.out.println("Please choose how to initialize the System");
            System.out.println("1 for Automatic Initialization and 2 for Empty Database");
            int choice = in.nextInt();
            if(choice == 1)
                init();
            while(true)
            {
                System.out.println("Please enter the menu number You wish to enter");
                System.out.println("Main Menu:\n1) Drivers Menu\n2) Trucks Menu\n3) Locations Menu\n4) Orders Menu\n" +
                        "5) Deliveries Menu\n6) Exit System");
                choice = in.nextInt();
                mainMenu(choice);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static void mainMenu(int choice)
    {
        switch(choice)
        {
            case 1:
                driversMenu();
                break;
            case 2:
                trucksMenu();
                break;
            case 3:
                locationsMenu();
                break;
            case 4:
                ordersMenu();
                break;
            case 5:
                deliveriesMenu();
                break;
            case 6:
                System.exit(0);
                break;
        }
    }

    public static void driversMenu()
    {
        System.out.println("Please enter the number of the function you wish to do");
        System.out.println("Drivers Menu:\n1) Create New Driver\n2) Delete Driver\n3) Change License Expiration Date\n" +
                "4) Change License Type\n5) Back To Main Menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String id, name, licenseType, licenseExpDate;
        int branchId;
        Date date;
        try
        {
            switch (choice)
            {
                case 1:
                    System.out.println("Please enter driver details: branch id ");
                    branchId = in.nextInt();

                    WorkerDTO w=MenuWorkers.createWorkerWithoutAdd();
                    System.out.println("Please enter driver details: licence type, expiration date");
                    licenseType = in.next();
                    licenseExpDate = in.next();
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpDate);
                    facade.createDriver(w, licenseType, date);
                    break;
                case 2:
                    System.out.println("Please enter the driver id that you want to erase from the system");
                    id = in.next();
                    facade.removeDriver(id);
                    break;
                case 3:
                    System.out.println("Please enter the driver id and the new expiration date");
                    id = in.next();
                    licenseExpDate = in.next();
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpDate);
                    facade.updateExpDate(id, date);
                    break;
                case 4:
                    System.out.println("Please enter the driver id and the new license type");
                    id = in.next();
                    licenseType = in.next();
                    facade.updateLicenseType(id, licenseType);
                    break;
                case 5:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nYou entered wrong details, please try again");
        }
    }

    public static void trucksMenu()
    {
        System.out.println("Please enter the number of the function you wish to do");
        System.out.println("Trucks Menu:\n1) Create New Truck\n2) Delete Truck\n3) Back To Main Menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String id, name;
        double netoWeight, totalWeight;
        try
        {
            switch (choice)
            {
                case 1:
                    System.out.println("Please enter truck details: truck id, model, neto weight, total weight");
                    id = in.next();
                    name = in.next();
                    netoWeight = in.nextDouble();
                    totalWeight = in.nextDouble();
                    Response res=facade.createTruck(id, name, netoWeight, totalWeight);
                    if (res.isErrorOccurred()){
                        System.out.println(res.getErrorMessage());
                    }
                    break;
                case 2:
                    System.out.println("Please enter the truck id that you want to erase from the system");
                    id = in.next();
                    facade.removeTruck(id);
                    break;
                case 3:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    public static void locationsMenu()
    {
        System.out.println("Please enter the number of the function you wish to do");
        System.out.println("Locations Menu:\n1) Create New Location\n2) Delete Location\n3) Change Telephone Number\n" +
                "4) Change Contact Name\n5) Back To Main Menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String  name, licenseType, licenseExpDate, s1, s2;
        int id;
        try
        {
            switch (choice)
            {
                case 1:
                    System.out.println("Please enter location details: location id, name, address, telephone number,\n" +
                            "contact name, shipping area");
                    id = in.nextInt();
                    name = in.next();
                    licenseType = in.next();
                    licenseExpDate = in.next();
                    s1 = in.next();
                    s2 = in.next();
                    facade.createLocation(id, name, licenseType, licenseExpDate, s1, s2);
                    break;
                case 2:
                    System.out.println("Please enter the location id that you want to erase from the system");
                    id = in.nextInt();
                    facade.removeLocation(id);
                    break;
                case 3:
                    System.out.println("Please enter location id and the new telephone number");
                    id = in.nextInt();
                    s1 = in.next();
                    facade.updateTelNumber(id, s1);
                    break;
                case 4:
                    System.out.println("Please enter location id and the new contact name");
                    id = in.nextInt();
                    s1 = in.next();
                    facade.updateContactName(id, s1);
                    break;
                case 5:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    public static void ordersMenu()
    {
        System.out.println("Please enter the number of the function you wish to do");
        System.out.println("Orders Menu:\n1) Create New Order\n2) Delete Order\n3) Add Item To Order\n" +
                "4) Remove Item From Order\n5) Change Quantity Of Item In Order\n6) Change Total Weight Of Order\n" +
                "7) Back To Main Menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String name, s1, licenseExpDate;
        int id;
        int quantity;
        double totalWeight;
        try
        {
            switch (choice)
            {
                case 1:
                    Map<String, Integer> items = new HashMap<>();
                    System.out.println("To create order details please first enter items - itemName, quantity\n" +
                            "to finish please enter x");
                    s1 = in.next();
                    while(s1.compareTo("x") != 0)
                    {
                        quantity = in.nextInt();
                        items.put(s1, quantity);
                        s1 = in.next();
                    }
                    System.out.println("Please enter order details: id, supplier id, location id, total weight");
                    id = in.nextInt();
                    name = in.next();
                    licenseExpDate = in.next();
                    totalWeight = in.nextDouble();
                    facade.createOrder(id, items, name, licenseExpDate, totalWeight);
                    break;
                case 2:
                    System.out.println("Please enter the order id that you want to erase from the system");
                    id = in.nextInt();
                    facade.removeOrder(id);
                    break;
                case 3:
                    System.out.println("Please enter order id, item name, quantity");
                    id = in.nextInt();
                    name = in.next();
                    quantity = in.nextInt();
                    facade.addItem(id, name, quantity);
                    break;
                case 4:
                    System.out.println("Please enter order id, item name");
                    id = in.nextInt();
                    name = in.next();
                    facade.removeItem(id, name);
                    break;
                case 5:
                    System.out.println("Please enter order id, item name, quantity");
                    id = in.nextInt();
                    name = in.next();
                    quantity = in.nextInt();
                    facade.updateQuantity(id, name, quantity);
                    break;
                case 6:
                    System.out.println("Please enter order id, total weight");
                    id = in.nextInt();
                    totalWeight = in.nextDouble();
                    facade.updateTotalWeight(id, totalWeight);
                    break;
                case 7:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    public static void deliveriesMenu()
    {
        System.out.println("Please enter the number of the function you wish to do");
        System.out.println("Deliveries Menu:\n1) Create New Delivery\n2) Delete Delivery\n3) Change Delivery Date\n" +
                "4) Change Delivery Time\n5) Change Weight Of Delivery\n6) Change Truck For Delivery\n" +
                "7) Change Driver For Delivery\n8) Add Order And Location\n9) Remove Order And Location\n" +
                "10) Change Status\n11) Display deliveries\n12) Back To Main Menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String id, s1,name, licenseType, licenseExpDate, s2;
        Date date;
        double totalWeight;
        try
        {
            switch (choice)
            {
                case 1:
                    List<String> targetLocations = new ArrayList<>();
                    List<String> orders = new ArrayList<>();
                    System.out.println("To create delivery details please first enter target locations," +
                            "\nTo finish please x");
                    s1 = in.next();
                    while(s1.compareTo("x") != 0)
                    {
                        targetLocations.add(s1);
                        s1 = in.next();
                    }
                    System.out.println("To create delivery details please first enter orders," +
                            "\nTo finish please x");
                    s1 = in.next();
                    while(s1.compareTo("x") != 0)
                    {
                        orders.add(s1);
                        s1 = in.next();
                    }
                    System.out.println("Please ender delivery details: id, delivery day, leaving time, driver id," +
                            "\nSource location, truck id");
                    id = in.next();
                    licenseExpDate = in.next();
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpDate);
                    s1 = in.next();
                    Time newTime1 = Time.valueOf(s1);
                    name = in.next();
                    licenseType = in.next();
                    s2 = in.next();
                    facade.createDelivery(id, date, newTime1, name, licenseType, targetLocations, s2, orders);
                    break;
                case 2:
                    System.out.println("Please enter the delivery id that you want to erase from the system");
                    id = in.next();
                    facade.removeDelivery(id);
                    break;
                case 3:
                    System.out.println("Please enter the delivery id and the new delivery day");
                    id = in.next();
                    licenseExpDate = in.next();
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpDate);
                    facade.updateDeliveryDate(id, date);
                    break;
                case 4:
                    System.out.println("Please enter the delivery id and the new delivery leaving time");
                    id = in.next();
                    s1 = in.next();
                    Time newTime = Time.valueOf(s1);
                    facade.updateLeavingTime(id, newTime);
                    break;
                case 5:
                    System.out.println("Please enter delivery id, new weight of delivery");
                    id = in.next();
                    totalWeight = in.nextDouble();
                    facade.updateWeight(id, totalWeight);
                    break;
                case 6:
                    System.out.println("Please enter delivery id, truck id");
                    id = in.next();
                    s1 = in.next();
                    facade.updateTruckId(id, s1);
                    break;
                case 7:
                    System.out.println("Please enter delivery id, driver id");
                    id = in.next();
                    s1 = in.next();
                    facade.updateDriverId(id, s1);
                    break;
                case 8:
                    System.out.println("Please enter delivery id, location id, order id");
                    id = in.next();
                    s1 = in.nextInt();
                    s2 = in.next();
                    facade.addOrderAndLocation(id, s1, s2);
                    break;
                case 9:
                    System.out.println("Please enter delivery id, location id, order id");
                    id = in.next();
                    s1 = in.next();
                    s2 = in.next();
                    facade.removeOrderAndLocation(id, s1, s2);
                    break;
                case 10:
                    System.out.println("Please enter delivery id, new delivery status that could be" +
                            "\nInProgress or Done");
                    id = in.next();
                    s1 = in.next();
                    facade.updateStatus(id, s1);
                    break;
                case 11:
                    facade.displayDeliveries();
                case 12:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    public static void init()
    {
        try
        {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2030");
            //facade.createDriver("203613310", "eden", "A", date);
            //facade.createDriver("313577645", "yarin", "B", date);
            //facade.createDriver("123456789", "reem", "C", date);
            facade.createLocation("1", "superli", "lachish 151 shoham", "0543160553", "avi", "Center");
            facade.createLocation("2", "maxstock", "shoham 26 haifa", "0504616909", "ben", "North");
            facade.createLocation("3", "shufersal", "azrieli tel aviv", "0543160550", "yossef", "Center");
            facade.createLocation("4", "tara", "bialik 32 ramat gan", "0581234567", "ronen", "Center");
            facade.createLocation("5", "tnuva", "rabin 12 beer sheva", "0538523644", "moshe", "South");
            facade.createLocation("6", "osem", "shimshon 24 krayot", "0528549847", "lavi", "North");
            facade.createTruck("2360154", "volvo", 1000.0, 4500.0);
            facade.createTruck("30122623", "chevrolet", 5000.0, 9000.5);
            facade.createTruck("11122333", "honda", 10000.0, 15000.0);
            Map<String, Integer> items1 = new HashMap<String, Integer>() {
                {
                    put("milk", 20);
                    put("pasta", 50);
                    put("chocolate", 10);
                    put("cola", 10);
                }
            };
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
            facade.createOrder("12", items1, "487", "1", 1000.0);
            facade.createOrder("34", items2, "159", "2", 3500.0);
            facade.createOrder("56", items3, "263", "3", 2500.0);
            facade.createOrder("78", items4, "546", "1", 2000.0);
            facade.createOrder("98", items5, "943", "3", 2000.0);
            Date newDate1 = new GregorianCalendar(2021, Calendar.MAY, 11).getTime();
            Date newDate2 = new GregorianCalendar(2021, Calendar.DECEMBER, 31).getTime();
            Date newDate3 = new GregorianCalendar(2021, Calendar.JULY, 7).getTime();
            Time newTime1 = Time.valueOf("12:30:00");
            Time newTime2 = Time.valueOf("13:00:00");
            Time newTime3 = Time.valueOf("11:25:30");
            List<String> centerLocations = new ArrayList<String>() {
                {
                    add("1");
                    add("3");
                }
            };
            List<String> northLocations = new ArrayList<String>() {
                {
                    add("2");
                }
            };
            List<String> orders1 = new ArrayList<String>() {
                {
                    add("12");
                    add("56");
                }
            };
            List<String> orders2 = new ArrayList<String>() {
                {
                    add("34");
                }
            };
            List<String> orders3 = new ArrayList<String>() {
                {
                    add("78");
                    add("98");
                }
            };
            facade.createDelivery("101", newDate1, newTime1, "203613310", "4", centerLocations, "2360154", orders1);
            facade.createDelivery("102", newDate2, newTime2, "313577645", "15", northLocations, "30122623", orders2);
            facade.createDelivery("103", newDate3, newTime3, "123456789", "6", centerLocations, "11122333", orders3);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}