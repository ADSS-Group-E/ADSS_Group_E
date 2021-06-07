package BusinessLayer;

import BusinessLayer.Inventory.Controllers.ReportController;
import BusinessLayer.Inventory.Product;
import BusinessLayer.Inventory.Report;
import BusinessLayer.Supplier.Item;
import BusinessLayer.Supplier.SupplierController;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.util.ArrayList;

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
        // chooseBestSupplierForItems will assume one supplier has all items
        if (items.size() > 0) {
            int supplierNum = sCont.chooseBestSupplierForItems(items);
            // TODO: make needsDelivery a prompt
            return sCont.proposeOrder(supplierNum, true, false, items); // TODO what to do about needsDelivery?
        }
        else return "";
    }


    public void createOrderFromReport(int reportId, boolean needsDelivery){
        Report report = rCont.getReport(reportId);
        ArrayList<String[]> items = makeItemsFromReport(report);
        int supplierNum = sCont.chooseBestSupplierForItems(items);
        sCont.createOrder(supplierNum, needsDelivery, false, items); // TODO what to do about needsDelivery?
    }

    private ArrayList<String[]> makeItemsFromReport(Report report){
        // Need to somehow link from inventory items to supplier items.
        ArrayList<String[]> items = new ArrayList<>();
        ArrayList<Product> reportProducts = report.getProducts();
        for (Product product:
             reportProducts) {
            // TODO: get min amount and total amount in product to calculate quantity to order
            items.add(new String[]{product.getId() + "", product.getName(), (product.getMinAmount()- product.getAmountInStorage()- product.getAmountInStore())*2 + "", (int)product.getBuyingPrice() + "", ""});
        }
        return items;
    }
}
