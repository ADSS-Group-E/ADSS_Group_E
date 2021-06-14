package PresentationLayer.Workers_Transport;

import BusinessLayer.Workers_Transport.DeliveryPackage.DeliveryFacade;
import BusinessLayer.Workers_Transport.Response;
import BusinessLayer.Workers_Transport.WorkersPackage.WorkersFacade;
import DataAccessLayer.Workers_Transport.Repo;
import DataAccessLayer.Workers_Transport.Transports.DTO;
import PresentationLayer.Workers.DataTransferObjects.AvailableWorkDaysDTO;
import PresentationLayer.Workers.DataTransferObjects.BankAccountDTO;
import PresentationLayer.Workers.DataTransferObjects.HiringConditionsDTO;
import PresentationLayer.Workers.DataTransferObjects.WorkerDTO;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Menu_Transport {

    private static final DeliveryFacade deliveryFacade =DeliveryFacade.getInstance();
    private static final WorkersFacade workersFacade =WorkersFacade.getInstance();

    public static void createSystem(){
        Scanner in = new Scanner(System.in);

        try
        {
            System.out.println("Welcome to the Delivery System Manager");
            System.out.println("Please choose how to initialize the System");
            System.out.println("1 for Automatic Initialization and 2 for Empty Database");
            int choice = in.nextInt();
            switch (choice)
            {
                case 1:
                    //Repo.deleteDataBase();
                    Repo.createDatabase();
                    init();
                    break;

                case 2:
                    if(Repo.openDatabase())
                    {
                        Repo.deleteDataBase();
                        Repo.createDatabase();
                    }
                    break;



            }
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
        System.out.println("Drivers Menu:\n1) Change License Expiration Date\n" +
                "2) Change License Type\n3) Back To Main Menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String id, name, licenseType, licenseExpDate;
        int branchId;
        LocalDate date;
        try
        {
            switch (choice)
            {
                case 1:
                    System.out.println("Please enter the driver id");
                    id = in.next();
                    System.out.println("Please enter the new expiration date");
                    licenseExpDate = in.next();
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/ yyyy");
                    date = LocalDate.parse(licenseExpDate, df);
                    deliveryFacade.updateExpDate(id, date);
                    break;
                case 2:
                    System.out.println("Please enter the driver id");
                    id = in.next();
                    System.out.println("Please enter the new license type");
                    licenseType = in.next();
                    deliveryFacade.updateLicenseType(id, licenseType);
                    break;
                case 3:
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
        System.out.println("Trucks Menu:\n1) Create New Truck\n3) display trucks\n4) back to main menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String id, name;
        double netoWeight, totalWeight;
        Response res;
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
                    res= deliveryFacade.createTruck(id, name, netoWeight, totalWeight);
                    if (res.isErrorOccurred()){
                        System.out.println(res.getErrorMessage());
                    }
                    break;
                case 2:
                    System.out.println("Please enter the truck id that you want to erase from the system");
                    id = in.next();
                    res= deliveryFacade.removeTruck(id);
                    if (res.isErrorOccurred()){
                        System.out.println(res.getErrorMessage());
                    }
                    break;
                case 3:
                    deliveryFacade.printTrucks();
                    break;
                case 4:
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
        System.out.println("please enter the number of the function you wish to do");
        System.out.println("locations menu:\n1) create new location\n2) delete location\n3) change telephone number\n" +
                "4) change contact name\n5) display location\n6) back to main menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String  name, licenseType, licenseExpDate, s1, s2;
        int id;
        try
        {
            switch (choice)
            {
                case 1:
                    System.out.println("please enter location details: location id, name, address, telephone number,\n" +
                            "contact name, shipping area");
                    id = in.nextInt();
                    name = in.next();
                    licenseType = in.next();
                    licenseExpDate = in.next();
                    s1 = in.next();
                    s2 = in.next();
                    deliveryFacade.createLocation(id, name, licenseType, licenseExpDate, s1, s2);
                    break;
                case 2:
                    System.out.println("please enter the location id that you want to erase from the system");
                    id = in.nextInt();
                    deliveryFacade.removeLocation(id);
                    break;
                case 3:
                    System.out.println("please enter location id and the new telephone number");
                    id = in.nextInt();
                    s1 = in.next();
                    deliveryFacade.updateTelNumber(id, s1);
                    break;
                case 4:
                    System.out.println("please enter location id and the new contact name");
                    id = in.nextInt();
                    s1 = in.next();
                    deliveryFacade.updateContactName(id, s1);
                    break;
                case 5:
                    deliveryFacade.printLocations();
                    break;
                case 6:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nyou entered wrong details please try again");
        }
    }

    public static void ordersMenu()
    {
        System.out.println("please enter the number of the function you wish to do");
        System.out.println("orders menu:\n1) create new order\n2) delete order\n3) add item to order\n" +
                "4) remove item from order\n5) change quantity of item in order\n6) change total weight of order\n" +
                "7) display orders\n8) back to main menu");
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
                    System.out.println("to create order details please first enter items - itemName, quantity\n" +
                            "to finish please enter x");
                    s1 = in.next();
                    while(s1.compareTo("x") != 0)
                    {
                        quantity = in.nextInt();
                        items.put(s1, quantity);
                        s1 = in.next();
                    }
                    System.out.println("please enter order details: id, supplier id, location id, total weight");
                    id = in.nextInt();
                    name = in.next();
                    int loc = in.nextInt();
                    totalWeight = in.nextDouble();
                    deliveryFacade.createOrder(id, items, name, loc, totalWeight);
                    break;
                case 2:
                    System.out.println("please enter the order id that you want to erase from the system");
                    id = in.nextInt();
                    deliveryFacade.removeOrder(id);
                    break;
                case 3:
                    System.out.println("please enter order id, item name, quantity");
                    id = in.nextInt();
                    name = in.next();
                    quantity = in.nextInt();
                    deliveryFacade.addItem(id, name, quantity);
                    break;
                case 4:
                    System.out.println("please enter order id, item name");
                    id = in.nextInt();
                    name = in.next();
                    deliveryFacade.removeItem(id, name);
                    break;
                case 5:
                    System.out.println("please enter order id, item name, quantity");
                    id = in.nextInt();
                    name = in.next();
                    quantity = in.nextInt();
                    deliveryFacade.updateQuantity(id, name, quantity);
                    break;
                case 6:
                    System.out.println("please enter order id, total weight");
                    id = in.nextInt();
                    totalWeight = in.nextDouble();
                    deliveryFacade.updateTotalWeight(id, totalWeight);
                    break;
                case 7:
                    deliveryFacade.printOrders();
                    break;
                case 8:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nyou entered wrong details please try again");
        }
    }

    public static void deliveriesMenu()
    {
        System.out.println("please enter the number of the function you wish to do");
        System.out.println("deliveries menu:\n1) create new delivery\n2) delete delivery\n3) change delivery date\n" +
                "4) change delivery time\n5) change weight of delivery\n6) change truck for delivery\n" +
                "7) change driver for delivery\n8) add order and location\n9) remove order and location\n" +
                "10) change status\n11) display deliveries\n12) back to main menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String id,name, licenseType, deliveryDate, s2;
        Date date;
        int s1;
        double totalWeight;
        try
        {
            switch (choice)
            {
                case 1:
                    List<Integer> targetLocations = new ArrayList<>();
                    List<Integer> orders = new ArrayList<>();
                    System.out.println("to create delivery details please first enter target locations," +
                            "\nto finish please enter -1");
                    s1 = in.nextInt();
                    while(s1!=-1)
                    {
                        targetLocations.add(s1);
                        s1 = in.nextInt();
                    }
                    System.out.println("to create delivery details please first enter orders," +
                            "\nto finish please enter -1");
                    s1 = in.nextInt();
                    while(s1!=-1)
                    {
                        orders.add(s1);
                        s1 = in.nextInt();
                    }
                    System.out.println("please enter delivery id");
                    id=in.next();
                    System.out.println("please enter delivery day:");
                    LocalDate localDate;
                    do {
                        System.out.println("delivery day must be next week date");
                        deliveryDate = in.next();
                        date = new SimpleDateFormat("dd/MM/yyyy").parse(deliveryDate);
                        localDate=convertToLocalDateViaSqlDate(date);
                    }
                    while (!isDayNextWeek(localDate));
                    System.out.println("please enter leaving time (must be between 08:00-23:00):");
                    String ss1 = in.next();
                    Time newTime1 = Time.valueOf(ss1);
                    System.out.println("please enter: driver id," +
                            "\nsource location, truck id");
                    String name1 = in.next();
                    int loc = in.nextInt();
                    s2 = in.next();
                    deliveryFacade.createDelivery(id, date, newTime1, name1, loc, targetLocations, s2, orders);
                    break;
                case 2:
                    System.out.println("please enter the delivery id that you want to erase from the system");
                    id = in.next();
                    deliveryFacade.removeDelivery(id);
                    break;
                case 3:
                    System.out.println("please enter the delivery id and the new delivery day");
                    id = in.next();
                    deliveryDate = in.next();
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(deliveryDate);
                    deliveryFacade.updateDeliveryDate(id, date);
                    break;
                case 4:
                    System.out.println("please enter the delivery id and the new delivery leaving time");
                    id = in.next();
                    String d = in.next();
                    Time newTime = Time.valueOf(d);
                    deliveryFacade.updateLeavingTime(id, newTime);
                    break;
                case 5:
                    System.out.println("please enter delivery id, new weight of delivery");
                    id = in.next();
                    totalWeight = in.nextDouble();
                    deliveryFacade.updateWeight(id, totalWeight);
                    break;
                case 6:
                    System.out.println("please enter delivery id, truck id");
                    id = in.next();
                    String tid = in.next();
                    deliveryFacade.updateTruckId(id, tid);
                    break;
                case 7:
                    System.out.println("please enter delivery id, driver id");
                    id = in.next();
                    String temp = in.next();
                    deliveryFacade.updateDriverId(id, temp);
                    break;
                case 8:
                    System.out.println("please enter delivery id, location id, order id");
                    id = in.next();
                    s1 = in.nextInt();
                    int ss2 = in.nextInt();
                    deliveryFacade.addOrderAndLocation(id, s1, ss2);
                    break;
                case 9:
                    System.out.println("please enter delivery id, location id, order id");
                    id = in.next();
                    s1 = in.nextInt();
                    int sss2 = in.nextInt();
                    deliveryFacade.removeOrderAndLocation(id, s1, sss2);
                    break;
                case 10:
                    System.out.println("please enter delivery id, new delivery status that could be" +
                            "\nInTransit or Delivered");
                    id = in.next();
                    String sta= in.next();
                    deliveryFacade.updateStatus(id, sta);
                    break;
                case 11:
                    deliveryFacade.printDeliveries();

                    break;
                case 12:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nyou entered wrong details please try again");
        }
    }

   /*
    public static void init()
    {
        try
        {
            BankAccountDTO bankAccountDTO1 = new BankAccountDTO("Bank Mizrahi-Tefahot", "216", "123451");
            BankAccountDTO bankAccountDTO2 = new BankAccountDTO("Bank Mizrahi-Tefahot", "216", "222221");
            BankAccountDTO bankAccountDTO3 = new BankAccountDTO("Bank Otsar Ha-Hayal", "318", "060001");
            LocalDate startDate1 = LocalDate.of(2020, 5, 23);
            LocalDate startDate2 = LocalDate.of(2021, 1, 15);
            LocalDate startDate3 = LocalDate.of(2021, 2, 22);
            HiringConditionsDTO hiringConditionsDTO1 = new HiringConditionsDTO(10000, "Baillie Gifford American", 10, 5);
            HiringConditionsDTO hiringConditionsDTO2 = new HiringConditionsDTO(12000, "Baillie Gifford American", 7, 3);
            HiringConditionsDTO hiringConditionsDTO3 = new HiringConditionsDTO(8000, "Baillie Gifford American", 12, 7);
            Boolean favoriteShifts1[][] = new Boolean[][]{{true, false}, {true, true}, {false, false}, {true, true}, {false, true}, {true, false}, {true, true}};
            Boolean favoriteShifts2[][] = new Boolean[][]{{true, false}, {true, true}, {false, true}, {true, true}, {true, true}, {false, false}, {true, true}};
            Boolean favoriteShifts3[][] = new Boolean[][]{{true, true}, {false, false}, {false, false}, {true, true}, {false, true}, {true, false}, {true, true}};
            Boolean cantWork1[][] = new Boolean[][]{{false, true}, {false, false}, {true, true}, {false, false}, {true, false}, {false, true}, {false, false}};
            Boolean cantWork2[][] = new Boolean[][]{{false, true}, {false, false}, {true, false}, {false, false}, {false, false}, {true, true}, {false, false}};
            Boolean cantWork3[][] = new Boolean[][]{{false, false}, {true, true}, {true, true}, {false, false}, {true, false}, {false, true}, {false, false}};
            AvailableWorkDaysDTO availableWorkDaysDTO1 = new AvailableWorkDaysDTO(favoriteShifts1, cantWork1);
            AvailableWorkDaysDTO availableWorkDaysDTO2 = new AvailableWorkDaysDTO(favoriteShifts2, cantWork2);
            AvailableWorkDaysDTO availableWorkDaysDTO3 = new AvailableWorkDaysDTO(favoriteShifts3, cantWork3);
            Date expDate = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2030");
            WorkerDTO Yoad = new WorkerDTO("yarin", "peretz", "323079107", bankAccountDTO1, hiringConditionsDTO1, availableWorkDaysDTO1);
            WorkerDTO Omer = new WorkerDTO("eden", "bishela", "208060255", bankAccountDTO2, hiringConditionsDTO2, availableWorkDaysDTO2);
            WorkerDTO Gal = new WorkerDTO("Gal", "Brown", "207896321", bankAccountDTO3, hiringConditionsDTO3, availableWorkDaysDTO3);
            facade.createDriver("203613310", "eden", "A", expDate);
            facade.createDriver("313577645", "yarin", "B", expDate);
            facade.createDriver("123456789", "reem", "C", expDate);

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
    */

    public static boolean isDayNextWeek(LocalDate day){
        LocalDate upcomingSunday=LocalDate.now();
        switch (upcomingSunday.getDayOfWeek().getValue()){
            case 1:
                upcomingSunday=upcomingSunday.plusDays(6);
                break;
            case 2:
                upcomingSunday=upcomingSunday.plusDays(5);
                break;
            case 3:
                upcomingSunday=upcomingSunday.plusDays(4);
                break;
            case 4:
                upcomingSunday=upcomingSunday.plusDays(3);
                break;
            case 5:
                upcomingSunday=upcomingSunday.plusDays(2);
                break;
            case 6:
                upcomingSunday=upcomingSunday.plusDays(1);
                break;
            case 7:
                break;
        }
        LocalDate Nextsunday=upcomingSunday.plusDays(7);
        return  day.isBefore(Nextsunday)&&(day.isAfter(upcomingSunday)||day.isEqual(upcomingSunday)) ? true : false;
    }
    public static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public static void init()
    {
        try
        {
            BankAccountDTO bankAccountDTO1 = new BankAccountDTO("Bank Mizrahi-Tefahot", "216", "123451");
            BankAccountDTO bankAccountDTO2 = new BankAccountDTO("Bank Mizrahi-Tefahot", "216", "222221");
            BankAccountDTO bankAccountDTO3 = new BankAccountDTO("Bank Otsar Ha-Hayal", "318", "060001");
            LocalDate startDate1 = LocalDate.of(2020, 5, 23);
            LocalDate startDate2 = LocalDate.of(2021, 1, 15);
            LocalDate startDate3 = LocalDate.of(2021, 2, 22);
            HiringConditionsDTO hiringConditionsDTO1 = new HiringConditionsDTO(10000, "Baillie Gifford American", 10, 5);
            HiringConditionsDTO hiringConditionsDTO2 = new HiringConditionsDTO(12000, "Baillie Gifford American", 7, 3);
            HiringConditionsDTO hiringConditionsDTO3 = new HiringConditionsDTO(8000, "Baillie Gifford American", 12, 7);
            Boolean favoriteShifts1[][] = new Boolean[][]{{true, false}, {true, true}, {false, false}, {true, true}, {false, true}, {true, false}, {true, true}};
            Boolean favoriteShifts2[][] = new Boolean[][]{{true, false}, {true, true}, {false, true}, {true, true}, {true, true}, {false, false}, {true, true}};
            Boolean favoriteShifts3[][] = new Boolean[][]{{true, true}, {false, false}, {false, false}, {true, true}, {false, true}, {true, false}, {true, true}};
            Boolean cantWork1[][] = new Boolean[][]{{false, true}, {false, false}, {true, true}, {false, false}, {true, false}, {false, true}, {false, false}};
            Boolean cantWork2[][] = new Boolean[][]{{false, true}, {false, false}, {true, false}, {false, false}, {false, false}, {true, true}, {false, false}};
            Boolean cantWork3[][] = new Boolean[][]{{false, false}, {true, true}, {true, true}, {false, false}, {true, false}, {false, true}, {false, false}};
            AvailableWorkDaysDTO availableWorkDaysDTO1 = new AvailableWorkDaysDTO(favoriteShifts1, cantWork1);
            AvailableWorkDaysDTO availableWorkDaysDTO2 = new AvailableWorkDaysDTO(favoriteShifts2, cantWork2);
            AvailableWorkDaysDTO availableWorkDaysDTO3 = new AvailableWorkDaysDTO(favoriteShifts3, cantWork3);
            Date expDate = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2030");
            WorkerDTO yarinw = new WorkerDTO("yarin", "peretz", "313577645", bankAccountDTO1, hiringConditionsDTO1, availableWorkDaysDTO1);
            WorkerDTO eden = new WorkerDTO("eden", "bishela", "208060254", bankAccountDTO2, hiringConditionsDTO2, availableWorkDaysDTO2);
            WorkerDTO Gal = new WorkerDTO("Gal", "Brown", "207894326", bankAccountDTO3, hiringConditionsDTO3, availableWorkDaysDTO3);
            //DriverDTO yarin= new DriverDTO(yarinw,"A",expDate);

            workersFacade.addWorker(yarinw, 1);
            DTO.Driver yarin=new DTO.Driver("313577645","A",expDate);
            deliveryFacade.createDriver(yarin,1);

            //facade.createDriver("313577645", "yarin", "B", expDate);
            //facade.createDriver("123456789", "reem", "C", expDate);
            deliveryFacade.createLocation(1, "superli", "lachish 151 shoham", "0543160553", "yossi", "center");
            deliveryFacade.createLocation(2, "maxstock", "shoham 26 haifa", "0504616909", "ben", "north");
            deliveryFacade.createLocation(3, "shufersal", "azrieli tel aviv", "0543160550", "ronit", "center");
            deliveryFacade.createLocation(4, "tara", "bialik 32 ramat gan", "0581234567", "moshe", "center");
            deliveryFacade.createLocation(5, "tnuva", "rabin 12 beer sheva", "0538523644", "assaf", "south");
            deliveryFacade.createLocation(6, "osem", "shimshon 24 krayot", "0528549847", "shoshana", "north");
            deliveryFacade.createTruck("2360154", "volvo", 1000.0, 4500.0);
            deliveryFacade.createTruck("30122623", "chevrolet", 5000.0, 9000.5);
            deliveryFacade.createTruck("11122333", "honda", 10000.0, 15000.0);
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
            deliveryFacade.createOrder(12, items1, "487", 1, 1000.0);
            deliveryFacade.createOrder(34, items2, "159", 2, 3500.0);
            deliveryFacade.createOrder(56, items3, "263", 3, 2500.0);
            deliveryFacade.createOrder(78, items4, "546", 1, 2000.0);
            deliveryFacade.createOrder(98, items5, "943", 3, 2000.0);
            Date newDate1 = new GregorianCalendar(2022, Calendar.MAY, 11).getTime();
            Date newDate2 = new GregorianCalendar(2020, Calendar.DECEMBER, 31).getTime();
            Date newDate3 = new GregorianCalendar(2020, Calendar.JULY, 7).getTime();
            Time newTime1 = Time.valueOf("12:30:00");
            Time newTime2 = Time.valueOf("13:00:00");
            Time newTime3 = Time.valueOf("11:25:30");
            List<Integer> centerLocations = new ArrayList<Integer>() {
                {
                    add(3);
                    add(4);
                }
            };
            List<Integer> northLocations = new ArrayList<Integer>() {
                {
                    add(2);
                }
            };
            List<Integer> orders1 = new ArrayList<Integer>() {
                {
                    add(12);
                    add(56);
                }
            };
            List<Integer> orders2 = new ArrayList<Integer>() {
                {
                    add(34);
                }
            };
            List<Integer> orders3 = new ArrayList<Integer>() {
                {
                    add(78);
                    add(98);
                }
            };
            deliveryFacade.createDelivery("101", newDate1, newTime1, "313577645", 1, centerLocations, "11122333", orders1);
            //facade.createDelivery("102", newDate2, newTime2, "312164668", 5, northLocations, "30122623", orders2);
            //facade.createDelivery("103", newDate3, newTime3, "123456789", 6, centerLocations, "11122333", orders3);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}