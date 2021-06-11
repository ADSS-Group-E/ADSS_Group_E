package PresentationLayer.Transport.OptionMenus;

import BusinessLayer.Workers_Transport.DeliveryPackage.DeliveryFacade;
import PresentationLayer.CommandLineInterface;
import PresentationLayer.Option;
import PresentationLayer.OptionsMenu;

import java.util.HashMap;
import java.util.Map;

public class OrdersOptionsMenu extends OptionsMenu {
    private final DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();

    public OrdersOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        int i = 1;
        options.put(i++, new Option("Create New Order", this::createNewOrder));
        options.put(i++, new Option("Delete Order", this::deleteOrder));
        options.put(i++, new Option("Add Item to Order", this::addItem));
        options.put(i++, new Option("Remove Item from Order", this::removeItem));
        options.put(i++, new Option("Change Quantity of Item in Order", this::changeQuantity));
        options.put(i++, new Option("Change Total Weight of Order", this::changeWeight));
        options.put(i++, new Option("Display Orders", this::displayOrders));
        options.put(i, new Option("Back", this::back));
    }

    private void createNewOrder() {
        String name, s1;
        int id;
        int quantity;
        double totalWeight;

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
        try{
            deliveryFacade.createOrder(id, items, name, loc, totalWeight);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void deleteOrder() {
        int id;
        System.out.println("please enter the order id that you want to erase from the system");
        id = in.nextInt();
        try{
            deliveryFacade.removeOrder(id);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void addItem() {
        String name;
        int id;
        int quantity;

        System.out.println("please enter order id, item name, quantity");
        id = in.nextInt();
        name = in.next();
        quantity = in.nextInt();
        try{
            deliveryFacade.addItem(id, name, quantity);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void removeItem() {
        String name;
        int id;

        System.out.println("please enter order id, item name");
        id = in.nextInt();
        name = in.next();
        try{
            deliveryFacade.removeItem(id, name);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }

    private void changeQuantity() {
        String name;
        int id;
        int quantity;

        System.out.println("please enter order id, item name, quantity");
        id = in.nextInt();
        name = in.next();
        quantity = in.nextInt();
        try{
            deliveryFacade.updateQuantity(id, name, quantity);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }

    }

    private void changeWeight() {
        int id;
        double totalWeight;

        System.out.println("please enter order id, total weight");
        id = in.nextInt();
        totalWeight = in.nextDouble();
        try{
            deliveryFacade.updateTotalWeight(id, totalWeight);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }

    }

    private void displayOrders() {
        try{
            deliveryFacade.printOrders();
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\nYou entered wrong details please try again");
        }
    }
}


