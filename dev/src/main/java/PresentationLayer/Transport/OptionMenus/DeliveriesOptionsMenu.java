package PresentationLayer.Transport.OptionMenus;

import BusinessLayer.Workers_Transport.DeliveryPackage.DeliveryFacade;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class DeliveriesOptionsMenu extends OptionsMenu {
    private final DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();

    public DeliveriesOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;
        options.put(i++, new Option("Create New Delivery", this::createNewDelivery));
        options.put(i++, new Option("Delete Delivery", this::deleteDelivery));
        options.put(i++, new Option("Change Delivery Date", this::changeDate));
        options.put(i++, new Option("Change Delivery Time", this::changeTime));
        options.put(i++, new Option("Change Delivery Weight", this::changeWeight));
        options.put(i++, new Option("Change Truck for Delivery", this::changeTruck));
        options.put(i++, new Option("Change Driver for Delivery", this::changeDriver));
        options.put(i++, new Option("Add Order and Location", this::addOrder));
        options.put(i++, new Option("Remove Order and Location", this::removeOrder));
        options.put(i++, new Option("Change Status", this::changeStatus));
        options.put(i++, new Option("Display Deliveries", this::displayDeliveries));

        options.put(i, new Option("Back", this::back));
    }

    private void createNewDelivery() {
        String id, deliveryDate, s2;
        Date date;
        int s1;
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
        try{
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
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void deleteDelivery() {
        String id;

        System.out.println("please enter the delivery id that you want to erase from the system");
        id = in.next();
        try{
            deliveryFacade.removeDelivery(id);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void changeDate() {
        String id, deliveryDate;
        Date date;

        System.out.println("please enter the delivery id and the new delivery day");
        id = in.next();
        deliveryDate = in.next();
        try{
            date = new SimpleDateFormat("dd/MM/yyyy").parse(deliveryDate);
            deliveryFacade.updateDeliveryDate(id, date);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void changeTime() {
        String id;

        System.out.println("please enter the delivery id and the new delivery leaving time");
        id = in.next();
        String d = in.next();
        Time newTime = Time.valueOf(d);
        try{
            deliveryFacade.updateLeavingTime(id, newTime);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void changeWeight() {
        String id;
        double totalWeight;
        System.out.println("please enter delivery id, new weight of delivery");
        id = in.next();
        totalWeight = in.nextDouble();
        try{
            deliveryFacade.updateWeight(id, totalWeight);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void changeTruck() {
        String id;
        System.out.println("please enter delivery id, truck id");
        id = in.next();
        String tid = in.next();
        try{
            deliveryFacade.updateTruckId(id, tid);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void changeDriver() {
        String id;
        System.out.println("please enter delivery id, driver id");
        id = in.next();
        String temp = in.next();
        try{
            deliveryFacade.updateDriverId(id, temp);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void addOrder() {
        String id;

        int s1;

        System.out.println("please enter delivery id, location id, order id");
        id = in.next();
        s1 = in.nextInt();
        int ss2 = in.nextInt();
        try{
            deliveryFacade.addOrderAndLocation(id, s1, ss2);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void removeOrder() {
        String id;
        int s1;

        System.out.println("please enter delivery id, location id, order id");
        id = in.next();
        s1 = in.nextInt();
        int sss2 = in.nextInt();
        try{
            deliveryFacade.removeOrderAndLocation(id, s1, sss2);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void changeStatus() {
        String id;
        System.out.println("please enter delivery id, new delivery status that could be" +
                "\nInTransit or Delivered");
        id = in.next();
        String sta= in.next();
        try{
            deliveryFacade.updateStatus(id, sta);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void displayDeliveries() {
        try{
            deliveryFacade.printDeliveries();
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    public static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

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

}



