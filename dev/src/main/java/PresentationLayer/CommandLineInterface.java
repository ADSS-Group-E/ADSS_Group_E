package PresentationLayer;


import BusinessLayer.Inventory.Controllers.*;
import BusinessLayer.OrderFromReportHandler;
import BusinessLayer.Supplier.SupplierController;
import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;
import PresentationLayer.Inventory.Options.*;
import PresentationLayer.MainOptionsMenu;
import PresentationLayer.Supplier.ServiceController;
import PresentationLayer.Supplier.SupplierOptionsMenu;

import java.time.LocalDateTime;
import java.util.*;

/**
 * This class represents the command line interface
 */
public class CommandLineInterface {


    private final Facade facade;
    private final OrderFromReportHandler orderFromReportHandler;
    private final ServiceController serviceController;
    private final MainOptionsMenu mainOptionsMenu;
    private final MainInventoryOptionsMenu mainInventoryOptionsMenu;
    private final SupplierOptionsMenu supplierOptionsMenu;
    private final ProductsOptionsMenu productsOptionsMenu;
    private final CategoriesOptionsMenu categoriesOptionsMenu;
    private final DiscountsOptionsMenu discountsOptionsMenu;
    private final ReportsOptionsMenu reportsOptionsMenu;

    // Getters
    public Facade getFacade() {
        return facade;
    }

    public OrderFromReportHandler getOrderFromReportHandler() {
        return orderFromReportHandler;
    }

    public ServiceController getServiceController() {
        return serviceController;
    }

    public MainInventoryOptionsMenu getMainInventoryOptionsMenu() { return mainInventoryOptionsMenu; }

    public SupplierOptionsMenu getSupplierOptionsMenu() { return supplierOptionsMenu; }

    public ProductsOptionsMenu getProductsOptionsMenu() {
        return productsOptionsMenu;
    }

    public CategoriesOptionsMenu getCategoriesOptionsMenu() {
        return categoriesOptionsMenu;
    }

    public DiscountsOptionsMenu getDiscountsOptionsMenu() {
        return discountsOptionsMenu;
    }

    public ReportsOptionsMenu getReportsOptionsMenu() {
        return reportsOptionsMenu;
    }

    public CommandLineInterface() {
        CategoryController cCont = new CategoryController();
        ProductController pCont = new ProductController(cCont);
        ReportController rCont = new ReportController();

        DiscountController dCont = new DiscountController(pCont);
        SupplierController sCont = new SupplierController();

        facade = new Facade(pCont, rCont, cCont, dCont);
        orderFromReportHandler = new OrderFromReportHandler(rCont,sCont);
        serviceController = new ServiceController(sCont);

        mainOptionsMenu = new MainOptionsMenu(this);
        mainInventoryOptionsMenu = new MainInventoryOptionsMenu(this);
        supplierOptionsMenu = new SupplierOptionsMenu(this);
        productsOptionsMenu = new ProductsOptionsMenu(this);
        categoriesOptionsMenu = new CategoriesOptionsMenu(this);
        discountsOptionsMenu = new DiscountsOptionsMenu(this);
        reportsOptionsMenu = new ReportsOptionsMenu(this);
    }

    // Loads sample data because there is no data access layer in this part of the project.
    public void loadSampleData(){
        facade.addCategory(1,"Juice");
        facade.addCategory(new CategoryDTO(2,"<500 ML",1));
        facade.addProduct(new ProductDTO(1, "Test Juice", "AB01","B13", "Test Company",10.5, 10.1, 5,2, -1, -1));
        facade.addItemToStore(1,1, LocalDateTime.of(2021,4,24,16,0));
        facade.addItemToStorage(1,2, LocalDateTime.of(2021,4,25,16,0));
        facade.addItemToStore(1,3, LocalDateTime.of(2021,4,1,16,0));
        facade.addItemToStorage(1,4, LocalDateTime.of(2021,4,1,16,0));
        ArrayList<Integer> cids = new ArrayList<>();
        ArrayList<Integer> pids = new ArrayList<>();
        cids.add(1);
        pids.add(1);
        facade.addDiscount(1,"Test Spring Discount", 0.1,
                LocalDateTime.of(2021,4,1,16,0),
                LocalDateTime.of(2021,5,1,16,0),
                cids,
                pids,
                "Selling");

        serviceController.initialize();
    }
//    public ProductDTO(int pid, String name, String storageLocation, String storeLocation, int amountInStorage, int amountInStore, String manufacturer, double buyingPrice, double sellingPrice, int minAmount) {
    public void run() {
        System.out.println("SUPER-LI Inventory and Supplier Module");
        mainOptionsMenu.enter();
    }
}


