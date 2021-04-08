package PresentationLayer;


import BusinessLayer.Facade;
import PresentationLayer.Options.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class CommandLineInterface {


    private Facade facade;
    private MainOptionsMenu mainOptionsMenu;
    private ProductsOptionsMenu productsOptionsMenu;
    private CategoriesOptionsMenu categoriesOptionsMenu;
    private DiscountsOptionsMenu discountsOptionsMenu;
    private ReportsOptionsMenu reportsOptionsMenu;

    public Facade getFacade() {
        return facade;
    }

    public MainOptionsMenu getMainOptionsMenu() {
        return mainOptionsMenu;
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
        facade.addProduct(new ProductDTO(1, "Test Juice", "AB01","B13", "Test Company",10.5, 10.1, 5));
        facade.addItemToStore(1,1, LocalDateTime.of(2021,4,24,16,0));
        facade.addItemToStorage(1,2, LocalDateTime.of(2021,4,25,16,0));
        ArrayList<Integer> pids = new ArrayList<>();
        pids.add(1);
        facade.addDiscount(new DiscountDTO(1,"Test Spring Discount", 0.1,
                LocalDateTime.of(2021,4,1,16,0),
                LocalDateTime.of(2021,5,1,16,0),
                pids,
                "Selling"));
    }
//    public ProductDTO(int pid, String name, String storageLocation, String storeLocation, int amountInStorage, int amountInStore, String manufacturer, double buyingPrice, double sellingPrice, int minAmount) {
    public void run() {
        Scanner in = new Scanner(System.in);
        System.out.println("SUPER-LI Inventory Module");
        while (true){
            mainOptionsMenu.displayOptions();
            int choice = in.nextInt();
            mainOptionsMenu.chooseOption(choice);
        }
    }
}


