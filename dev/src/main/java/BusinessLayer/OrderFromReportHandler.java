package BusinessLayer;

import BusinessLayer.Inventory.Controllers.ReportController;
import BusinessLayer.Inventory.Product;
import BusinessLayer.Inventory.Report;
import BusinessLayer.Supplier.Item;
import BusinessLayer.Supplier.SupplierController;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderFromReportHandler {
    private final ReportController rCont;
    private final SupplierController sCont;

    public OrderFromReportHandler(ReportController rCont, SupplierController sCont) {
        this.rCont = rCont;
        this.sCont = sCont;
    }


    public String proposeOrderFromReport(int reportId){
        Report report = rCont.getReport(reportId);
        ArrayList<String[]> items = makeItemsFromReport(report);
        StringBuilder orders = new StringBuilder();
        // chooseBestSupplierForItems will assume one supplier has all items
        if (items.size() > 0) {
            ArrayList<String[]> supplierNum = sCont.chooseBestSupplierForItems(items);
            // TODO: make needsDelivery a prompt
            for (String[] order : supplierNum) {
                ArrayList<String[]> item = new ArrayList<>();
                item.add(Arrays.copyOfRange(order, 1, order.length));
                orders.append(sCont.proposeOrder(Integer.parseInt(order[0]), true, false, item)); // TODO what to do about needsDelivery?
            }
        }
        return orders.toString();
    }


    public void createOrderFromReport(int reportId, boolean needsDelivery){
        Report report = rCont.getReport(reportId);
        ArrayList<String[]> items = makeItemsFromReport(report);
        ArrayList<String[]> supplierNum = sCont.chooseBestSupplierForItems(items);
        for (String[] order : supplierNum) {
            ArrayList<String[]> item = new ArrayList<>();
            item.add(Arrays.copyOfRange(order, 1, order.length));
            sCont.createOrder(Integer.parseInt(order[0]), needsDelivery, false, item); // TODO what to do about needsDelivery?
        }
    }

    private ArrayList<String[]> makeItemsFromReport(Report report){
        // Need to somehow link from inventory items to supplier items.
        ArrayList<String[]> items = new ArrayList<>();
        ArrayList<Product> reportProducts = report.getProducts();
        for (Product product:
             reportProducts) {
            // TODO: get min amount and total amount in product to calculate quantity to order
            items.add(new String[]{product.getPid() + "", product.getName(), (product.getMinAmount()- product.getAmountInStorage()- product.getAmountInStore())*2 + "", (int)product.getBuyingPrice() + "", ""});
        }
        return items;
    }
}
