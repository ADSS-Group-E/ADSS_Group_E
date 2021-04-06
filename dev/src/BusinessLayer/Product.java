package BusinessLayer;

import PresentationLayer.ProductDTO;

public class Product {
    private int pid;
    private String name;
    private String storageLocation;
    private String storeLocation;
    private int amountInStorage;
    private int amountInStore;
    private String manufacturer;
    private double buyingPrice;
    private double sellingPrice;
    private int minAmount;

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public int getAmountInStorage() {
        return amountInStorage;
    }

    public int getAmountInStore() {
        return amountInStore;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public Product(int pid, String name, String storageLocation, String storeLocation, int amountInStorage, int amountInStore, String manufacturer, double buyingPrice, double sellingPrice, int minAmount) {
        this.pid = pid;
        this.name = name;
        this.storageLocation = storageLocation;
        this.storeLocation = storeLocation;
        this.amountInStorage = amountInStorage;
        this.amountInStore = amountInStore;
        this.manufacturer = manufacturer;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
    }

    public Product(ProductDTO other) {
        this.pid = other.getPid();
        this.name = other.getName();
        this.storageLocation = other.getStorageLocation();
        this.storeLocation = other.getStoreLocation();
        this.amountInStorage = other.getAmountInStorage();
        this.amountInStore = other.getAmountInStore();
        this.manufacturer = other.getManufacturer();
        this.buyingPrice = other.getBuyingPrice();
        this.sellingPrice = other.getSellingPrice();
        this.minAmount = other.getMinAmount();
    }
}
