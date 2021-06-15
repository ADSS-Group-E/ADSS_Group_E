package BusinessLayer;

import BusinessLayer.Inventory.Controllers.ReportController;
import BusinessLayer.Inventory.DomainObjects.Product;
import BusinessLayer.Inventory.DomainObjects.Reports.Report;
import BusinessLayer.Supplier.SupplierController;

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
            return sCont.proposeOrder(supplierNum, true, false, items, 10 * items.size(), 1); // TODO what to do about needsDelivery?
        }
        else return "";
    }


    public void createOrderFromReport(int reportId, boolean needsDelivery){
        Report report = rCont.getReport(reportId);
        ArrayList<String[]> items = makeItemsFromReport(report);
        int supplierNum = sCont.chooseBestSupplierForItems(items);
        sCont.createOrder(supplierNum, needsDelivery, false, items, 10 * items.size(), 1); // TODO what to do about needsDelivery?
    }

    private ArrayList<String[]> makeItemsFromReport(Report report){
        // Need to somehow link from inventory items to supplier items.
        ArrayList<String[]> items = new ArrayList<>();
        ArrayList<Product> reportProducts = report.getProducts();
        for (Product product:
             reportProducts) {
            // TODO: get min amount and total amount in product to calculate quantity to order
            items.add(new String[]{product.getId() + "", product.getName(), (int)(product.getSellingPrice()*3/4) + "", (product.getMinAmount()- product.getAmountInStorage()- product.getAmountInStore())*2 + "",""});
        }
        return items;
    }
}
