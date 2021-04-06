package PresentationLayer;


import BusinessLayer.Facade;
import PresentationLayer.Options.MainOptionsMenu;
import PresentationLayer.Options.ProductsOptionsMenu;

import java.util.Scanner;

public class CommandLineInterface {


    private Facade facade;
    private MainOptionsMenu mainOptionsMenu;
    private ProductsOptionsMenu productsOptionsMenu;

    public Facade getFacade() {
        return facade;
    }

    public MainOptionsMenu getMainOptionsMenu() {
        return mainOptionsMenu;
    }

    public ProductsOptionsMenu getProductsOptionsMenu() {
        return productsOptionsMenu;
    }

    public CommandLineInterface() {
        facade = new Facade();
        mainOptionsMenu= new MainOptionsMenu(this);
        productsOptionsMenu = new ProductsOptionsMenu(this);
    }

    public void loadSampleData(){
        facade.addProduct(new ProductDTO(1, "Test Juice", "AB01","B13",42,42,"Test Company",10.5, 10.1, 5));
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


