package BusinessLayer;

import BusinessLayer.Inventory.Controllers.ReportController;
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

    // Returns the items that were ordered.
    public String proposeOrderFromReport(int reportId){
        Report report = rCont.getReport(reportId);
        ArrayList<String[]> items = makeItemsFromReport(report);
        int supplierNum = sCont.chooseBestSupplierForItems(items);
        return sCont.proposeOrder(supplierNum, true, false, items); // TODO what to do about needsDelivery?
    }

    // Returns the items that were ordered.
    public void createOrderFromReport(int reportId){
        Report report = rCont.getReport(reportId);
        ArrayList<String[]> items = makeItemsFromReport(report);
        int supplierNum = sCont.chooseBestSupplierForItems(items);
        sCont.createOrder(supplierNum, true, false, items); // TODO what to do about needsDelivery?
    }

    private ArrayList<String[]> makeItemsFromReport(Report report){
        return null;
    }
}
