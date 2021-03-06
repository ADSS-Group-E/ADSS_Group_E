package PresentationLayer.Supplier;

import BusinessLayer.Supplier.Order;
import BusinessLayer.Supplier.QuantityWriter;
import BusinessLayer.Supplier.SupplierController;
import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.QuantityWriterDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.util.ArrayList;
import java.util.HashMap;

public class ServiceController {

    public ServiceController(SupplierController sCont){
        supplierController = sCont;
    }

    private SupplierController supplierController;

    public void register(String name, int companyNumber, String paymentMethod, String bankAccount, ArrayList<String[]> items, ArrayList<String[]> contacts, ArrayList<Integer> supplyDays) {
        //enters a supplier to the supplier list
        supplierController.register(name, companyNumber, paymentMethod, bankAccount, items, contacts, supplyDays);
    }

    public void register(String name, int companyNumber, String paymentMethod, String bankAccount,
    ArrayList<String[]> items, ArrayList<String[]> contacts, int regCostumer,
    int minPrice, HashMap<Integer, Integer> discountSteps, ArrayList<Integer> supplyDays) {
        //enters a supplier to the supplier list with a quantity writer
              supplierController.register(name, companyNumber, paymentMethod, bankAccount, items, contacts,
              regCostumer, minPrice, discountSteps, supplyDays);
    }

    public int createOrder(int supplierNum, boolean needsDelivery, boolean constantDelivery, ArrayList<String[]> items, int weight, int locationID) {
        //creates an order for the supplier
        return supplierController.createOrder(supplierNum, needsDelivery, constantDelivery, items, weight, locationID);
    }

    public int getMaxDiscount() {
        //gets max discount that is possible to get from the system
        return supplierController.getMaxDiscount();
    }

    public ArrayList<String[]> getSuppliersInfo() {
        //gets the suppliers info in an array of strings
        return supplierController.getSuppliersInfo();
    }

    ArrayList<OrderDTO> getRegularOrdersToStrings() {
        //gets all the orders that come on a weekly basis
        return supplierController.getRegularOrdersToStrings();
    }

    ArrayList<SupplierItemDTO> getItemsFromSupplier(int supplierNum) {
        //gets all of the items information from a specific supplier that he sells
        return supplierController.getItemsFromSupplier(supplierNum);
    }

    ArrayList<String[]> getAllItems() {
        //gets all orders from all suppliers with all of their information including supplier number and more
        return supplierController.getAllItems();
    }

    public String[] getSpecificItem(int supplierNum, int itemNum) {
        //gets the item information using it's supplier number and item number
        return supplierController.getSpecificItem(supplierNum, itemNum);
    }

    public boolean updateSellerItemQuantity(int supplierNum, int itemNum, int quantity) {
        //updates the quantity the seller has left to offer from the item when an order takes place
        return supplierController.updateSellerItemQuantity(supplierNum, itemNum, quantity);
    }

    public boolean updateOrderItemQuantity(int itemNum, int orderNum, int quantity) {
        //updates the quantity the seller and the costumer have for the item when an update happens
        return supplierController.updateOrderItemQuantity(itemNum, orderNum, quantity);
    }

    public boolean deleteOrderItem(int itemNum, int orderNum) {
        //deletes an item from an order and updates seller quantity of the item
        return supplierController.deleteCostumerItem(itemNum, orderNum);
    }

    public boolean contains(String[] equal, ArrayList<String[]> list) {
        //checks if an item with the parameters supplied already exists in the item list
        return supplierController.contains(equal, list);
    }

    public void initialize() {
        //initializes system with existing objects
        supplierController.initialize();
    }

    public QuantityWriterDTO getQuantityWriter(int idx){
        //gets quantity writer for test purposes
        return supplierController.getQuantityWriter(idx);
    }

    public OrderDTO getOrderFromSupplier(int orderIdx){
        //gets specific order for test purposes
        return supplierController.getOrderFromSupplier(orderIdx);
    }

}