package BusinessLayer.Supplier;

import DataAccessLayer.Supplier.DataController;
import PresentationLayer.Supplier.DataTransferObjects.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SupplierController {
    private Contact contact;
    private Item item;
    private Order order;
    private QuantityWriter quantityWriter;
    private Supplier supplier;
    private DataController data;
    private final int maxDiscount = 100;

    public SupplierController() {
        contact = new Contact();
        item = new Item();
        order = new Order();
        quantityWriter = new QuantityWriter();
        supplier = new Supplier();
        data = new DataController();
    }

    public void initialize() {
        data = new DataController();
        ArrayList<String[]> supplierOneItems = new ArrayList<>();//supplier one items
        supplierOneItems.add(new String[]{10 + "", "Corn", 10 + "", 15000 + "", 5554 + ""});
        supplierOneItems.add(new String[]{50 + "","Popcorn", 20 + "", 5000 + "", 5666 + ""});
        supplierOneItems.add(new String[]{100 + "", "Candy Corn", 30 + "", 20000 + "", 8989 + ""});
        supplierOneItems.add(new String[]{1 + "", "Test Juice", 5 + "", 10000 + "", 111 + ""});
        supplierOneItems.add(new String[]{2 + "", "Test Milk", 5 + "", 10000 + "", 112 + ""});
        supplierOneItems.add(new String[]{3 + "", "Test Milk", 5 + "", 10000 + "", 113 + ""});
        ArrayList<String[]> supplierTwoItems = new ArrayList<>();//supplier two items
        supplierTwoItems.add(new String[]{54 + "", "Hot Dog", 10 + "", 15000 + "", 100 + ""});
        supplierTwoItems.add(new String[]{110 + "", "Corn Dog", 20 + "", 5000 + "", 206 + ""});
        supplierTwoItems.add(new String[]{66 + "", "Dog", 3000 + "", 10000 + "", 5041 + ""});
        ArrayList<String[]> contacts = new ArrayList<>();
        contacts.add(new String[]{"Tzahi", "tzahi@tzahi.com"});
        HashMap<Integer, Integer> discounts = new HashMap<>();
        discounts.put(1000, 10);
        discounts.put(2000, 15);
        discounts.put(4000, 20);
        ArrayList<Integer> supplyDays1 = new ArrayList<>();
        supplyDays1.add(7);
        supplyDays1.add(2);
        supplyDays1.add(3);
        ArrayList<Integer> supplyDays2 = new ArrayList<>();
        supplyDays2.add(7);
        supplyDays2.add(1);
        supplyDays2.add(2);
        supplyDays2.add(3);
        register("Amazon", 10, "Cheque", "8145441/24", supplierOneItems, contacts, 10, 500, discounts, supplyDays1);
        register("Google", 54, "Paypal", "15144/455", supplierTwoItems, contacts, 10, 500, discounts, supplyDays2);
        ArrayList<String[]> orderItems = new ArrayList<>();
        orderItems.add(new String[]{100 + "", "Candy Corn", 30 + "", 100 + "", 8989 + ""});
        createOrder(10, false, true, orderItems, 10000, 1);
        ArrayList<String[]> orderItems2 = new ArrayList<>();
        orderItems2.add(new String[]{66 + "", "Dog", 3000 + "", 10 + "", 5041 + ""});
        createOrder(54, true, true, orderItems2, 30000, 1);
    }

    public ArrayList<String[]> getSuppliersInfo() {
        ArrayList<String[]> suppliersInfo = new ArrayList<>();
        for (SupplierDTO supplier : data.getSuppliers()) {
            String[] info = {supplier.getCompanyNumber() + "", supplier.getName(),  supplier.getPaymentMethod(), supplier.getBankAccount()};
            suppliersInfo.add(info);
        }
        return suppliersInfo;
    }

    public void register(String name, int companyNumber, String paymentMethod,
                 String bankAccount, ArrayList<String[]> items, ArrayList<String[]> contacts, ArrayList<Integer> supplyDays){
        ArrayList<SupplierItemDTO> itemDTOs = new ArrayList<>();
        ArrayList<ContactDTO> contactDTOs = new ArrayList<>();
        //create an arraylist with size of the number of items
        //create an arraylist with size of the number of contacts
        for (String[] strings : items) { //fills the item
            itemDTOs.add(new SupplierItemDTO(Integer.parseInt(strings[0]), strings[1], Integer.parseInt(strings[2]), Integer.parseInt(strings[3]), strings[4], companyNumber));
        }
        for (String[] strings : contacts) { //fills the contacts
            contactDTOs.add(new ContactDTO(strings[0], strings[1], companyNumber));
        }
        //add a supplier
        SupplierDTO supplierDTO = new SupplierDTO(companyNumber, name, bankAccount, paymentMethod);
        supplierDTO.setItems(itemDTOs);
        supplierDTO.setContacts(contactDTOs);
        supplierDTO.setSupplyDays(supplyDays);
        data.insert(supplierDTO);
    }

    public void register(String name, int companyNumber, String paymentMethod, String bankAccount,
                         ArrayList<String[]> items, ArrayList<String[]> contacts, int regCostumer,
                         int minPrice, HashMap<Integer, Integer> discountSteps, ArrayList<Integer> supplyDays){
        //same as register only adds a quantity writer as well
        ArrayList<SupplierItemDTO> itemDTOs = new ArrayList<>();
        ArrayList<ContactDTO> contactDTOs = new ArrayList<>();
        ArrayList<DiscountStepDTO> discountStepDTOs = new ArrayList<>();
        for (String[] strings : items) {
            itemDTOs.add(new SupplierItemDTO(Integer.parseInt(strings[0]), strings[1], Integer.parseInt(strings[3]), Integer.parseInt(strings[2]), strings[4], companyNumber));
        }
        for (String[] strings : contacts) {
            contactDTOs.add(new ContactDTO(strings[0], strings[1], companyNumber));
        }
        for (Integer i : discountSteps.keySet()) {
            DiscountStepDTO discount = new DiscountStepDTO(i, discountSteps.get(i));
            discountStepDTOs.add(discount);
        }
        SupplierDTO supplierDTO = new SupplierDTO(companyNumber, name, bankAccount, paymentMethod);
        supplierDTO.setItems(itemDTOs);
        supplierDTO.setContacts(contactDTOs);
        supplierDTO.setSupplyDays(supplyDays);
        supplierDTO.setQuantityWriter(new QuantityWriterDTO(companyNumber, regCostumer, minPrice, discountStepDTOs));
        data.insert(supplierDTO);
    }

    public int createOrder(int supplierNum, boolean needsDelivery, boolean constantDelivery, ArrayList<String[]> items, int weight, int locationID){
        ArrayList<SupplierItemDTO> itemDTOs = new ArrayList<>();
        for (String[] strings : items) { //creates an item array from all the items provided
            itemDTOs.add(new SupplierItemDTO(Integer.parseInt(strings[0]), strings[1], Integer.parseInt(strings[3]), Integer.parseInt(strings[2]), strings[4], supplierNum));
        }
        //creates the order
        //adds the order
        OrderDTO orderDTO = new OrderDTO(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy-hh:mm:ss")), constantDelivery? 1 : 0, needsDelivery? 1 : 0, itemDTOs, weight, locationID);
        data.insert(orderDTO);
        return quantityWriter.calcPrice(data.getSupplier(supplierNum).getQuantityWriter(), order.getPrice(orderDTO)); //calculates it's price
    }

    public ArrayList<OrderDTO> getRegularOrdersToStrings() {
        return data.selectRO();
    }

    public ArrayList<String[]> getAllItems() {
        ArrayList<String[]> regItems = new ArrayList<>();
        ArrayList<SupplierItemDTO> items = data.getItems();
        for (SupplierItemDTO item : items) {
            regItems.add(new String[]{item.getId() + "",
                    item.getPrice() + "", item.getQuantity() + "", item.getCompanyNumber() + "", item.getOrderID() + ""});
        }
        return regItems;
    }

    public String[] getSpecificItem(int supplierNum, int itemNum) {
        ArrayList<SupplierItemDTO> sup = data.getSupplierItems(supplierNum);
        for (SupplierItemDTO item : sup) {
            if (item.getId() == itemNum)
                return new String[]{item.getId() + "", item.getName(), item.getPrice() + "", item.getQuantity() + "", item.getSupplierCN()};
        }
        return new String[]{};
    }

    public boolean updateSellerItemQuantity(int supplierNum, int itemNum, int quantity) {
        SupplierDTO sup = data.getSupplier(supplierNum);
        if (sup == null) return false; //if the supplier number not in range
        List<SupplierItemDTO> items = sup.getItems();
        if (itemNum >= items.size() || itemNum < 0) return false; //if the item number not in range
        SupplierItemDTO item = items.get(itemNum);
        if (quantity <= 0 || quantity > item.getQuantity()) return false; //if the quantity we order is 0 or big than the quantity the supplier offers
        item.setQuantity(item.getQuantity() - quantity); //sets quantity
        SupplierItemDTO supplierItemDTO = new SupplierItemDTO(itemNum, item.getName(), item.getQuantity(), item.getPrice(), "");
        data.update(supplierItemDTO);
        return true;
    }

    public boolean updateOrderItemQuantity(int itemNum, int orderNum, int newQuantity) {
        if (newQuantity <= 0) return false; //if the new quantity we order is illegal
        SupplierItemDTO item = data.select(itemNum, orderNum);
        int oldQuantity = item.getQuantity();
        SupplierItemDTO supItem = data.supplierSelect(itemNum, item.getCompanyNumber());
        int quan = supItem.getQuantity() - oldQuantity;
        supItem.setQuantity(supItem.getQuantity() + oldQuantity - newQuantity);
        item.setQuantity(newQuantity);
        data.update(supItem);
        data.update(item);
        return true;
    }

    public boolean deleteCostumerItem(int itemNum, int orderNum) {
        data.delete(itemNum, orderNum);
        return true;
    }

    public boolean contains(String[] equal, ArrayList<String[]> list) {
        for (String[] object : list) {
            boolean equals = true;
            for (int i = 0; i < equal.length; i++)
                if (!equal[i].equalsIgnoreCase(object[i])) {
                    equals = false;
                    i = object.length;
                }
            if (equals)
                return true;
        }
        return false;
    }

    public ArrayList<SupplierItemDTO> getItemsFromSupplier(int supplierNum) {
        return data.getSupplierItems(supplierNum); //get all items from a supplier
    }

    public QuantityWriterDTO getQuantityWriter(int idx){
        return data.getSupplier(idx).getQuantityWriter();
    }

    public OrderDTO getOrderFromSupplier(int orderIdx){
        return data.selectO(orderIdx);
    }

    public int getMaxDiscount() {
        return maxDiscount;
    }

    public int chooseBestSupplierForItems(ArrayList<String[]> items){
        // TODO Implement for OrderFromReportHandler
        return data.chooseBestSupplier(items);
    }

    public String proposeOrder(int supplierNum, boolean needsDelivery, boolean constantDelivery, ArrayList<String[]> items, int weight, int locationID){
        ArrayList<SupplierItemDTO> itemDTOs = new ArrayList<>();
        for (String[] strings : items) { //creates an item array from all the items provided
            itemDTOs.add(new SupplierItemDTO(Integer.parseInt(strings[0]), strings[1], Integer.parseInt(strings[3]), Integer.parseInt(strings[2]), strings[4], supplierNum));
        }
        //creates the order
        //adds the order
        OrderDTO order = new OrderDTO(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy-hh:mm:ss")), constantDelivery? 1 : 0, needsDelivery? 1 : 0, itemDTOs, weight, locationID);
        StringBuilder output = new StringBuilder();
        output.append(String.format("[Date: %s, Needs Delivery: %s, Periodic Delivery: %s]", order.getDate(), order.getNeedsDelivery() == 1, order.getPeriodicDelivery() == 1));
        output.append("\nItems:");
        for (SupplierItemDTO item : order.getOrderItems()) {
            output.append(String.format("\nID: %s\nPrice: %d\nQuantity: %d\nCompany Number: %d]", item.getId(), item.getPrice(), item.getQuantity(), item.getCompanyNumber()));
        }
        return output.toString();
    }

    public HashMap<OrderDTO, ArrayList<Integer>> getDeliveryOrders() {
        return data.getDeliveryOrders();
        /*for (OrderDTO ord : dels.keySet()) {
            for(int i : dels.get(ord))
                System.out.println(ord.getOrderItems().get(0).getCompanyNumber() + " " + i);
        }
        return dels;*/
    }

    public void cancelOrders(ArrayList<Integer> orders) {
        data.cancelOrders(orders);
    }
}

