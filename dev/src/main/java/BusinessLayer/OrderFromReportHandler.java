package BusinessLayer;

import BusinessLayer.Inventory.Controllers.ReportController;
import BusinessLayer.Inventory.Product;
import BusinessLayer.Inventory.Report;
import BusinessLayer.Supplier.Item;
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
        int supplierNum = sCont.chooseBestSupplierForItems(items);
        // TODO: make needsDelivery a prompt
        return sCont.proposeOrder(supplierNum, true, false, items); // TODO what to do about needsDelivery?
    }


    public void createOrderFromReport(int reportId, boolean needsDelivery){
        Report report = rCont.getReport(reportId);
        ArrayList<String[]> items = makeItemsFromReport(report);
        int supplierNum = sCont.chooseBestSupplierForItems(items);
        sCont.createOrder(supplierNum, needsDelivery, false, items); // TODO what to do about needsDelivery?
    }

    private ArrayList<String[]> makeItemsFromReport(Report report){
        // Need to somehow link from inventory items to supplier items.
        ArrayList<String[]> items;
        ArrayList<Product> reportProducts = report.getProducts();
        for (Product product:
             reportProducts) {

        }
        return null;
    }
}
