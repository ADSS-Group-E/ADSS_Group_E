package InventoryModule.PresentationLayer;


import InventoryModule.BusinessLayer.Controllers.Facade;
import InventoryModule.PresentationLayer.DataTransferObjects.CategoryDTO;
import InventoryModule.PresentationLayer.DataTransferObjects.ProductDTO;
import InventoryModule.PresentationLayer.Options.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * This class represents the command line interface
 */
public class CommandLineInterface {


    private final Facade facade;
    private final MainOptionsMenu mainOptionsMenu;
    private final ProductsOptionsMenu productsOptionsMenu;
    private final CategoriesOptionsMenu categoriesOptionsMenu;
    private final DiscountsOptionsMenu discountsOptionsMenu;
    private final ReportsOptionsMenu reportsOptionsMenu;

    // Getters
    public Facade getFacade() {
        return facade;
    }



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
        facade = new Facade();
        mainOptionsMenu= new MainOptionsMenu(this);
        productsOptionsMenu = new ProductsOptionsMenu(this);
        categoriesOptionsMenu = new CategoriesOptionsMenu(this);
        discountsOptionsMenu = new DiscountsOptionsMenu(this);
        reportsOptionsMenu = new ReportsOptionsMenu(this);
    }

    // Loads sample data because there is no data access layer in this part of the project.
    public void loadSampleData(){
        facade.addCategory(1,"Juice");
        facade.addCategory(new CategoryDTO(2,"<500 ML",1));
        facade.addProduct(new ProductDTO(1, "Test Juice", "AB01","B13", "Test Company",10.5, 10.1, 5,2));
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
    }
//    public ProductDTO(int pid, String name, String storageLocation, String storeLocation, int amountInStorage, int amountInStore, String manufacturer, double buyingPrice, double sellingPrice, int minAmount) {
    public void run() {
        System.out.println("SUPER-LI Inventory Module");
        mainOptionsMenu.enter();
    }
}


