package BusinessLayer;

import PresentationLayer.ProductDTO;
import dev.src.BusinessLayer.Discount;

import java.time.format.DateTimeFormatter;

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
    private Category category;
    private Category subCategory;
    private Category subSubCategory;

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

    public BusinessLayer.Category getCategory() {
        return category;
    }

    public void setCategory(BusinessLayer.Category category) {
        this.category = category;
    }

    public BusinessLayer.Category getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(BusinessLayer.Category subCategory) {
        this.subCategory = subCategory;
    }

    public BusinessLayer.Category getSubSubCategory() {
        return subSubCategory;
    }

    public void setSubSubCategory(BusinessLayer.Category subSubCategory) {
        this.subSubCategory = subSubCategory;
    }

    public Product(int pid, String name, String storageLocation, String storeLocation, int amountInStorage, int amountInStore, String manufacturer, double buyingPrice, double sellingPrice, int minAmount, BusinessLayer.Category category, BusinessLayer.Category subCategory, BusinessLayer.Category subSubCategory) {
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
        this.category = category;
        this.subCategory = subCategory;
        this.subSubCategory = subSubCategory;
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
        // TODO add categories to ProductDTO (?)
    }

    public void setDiscount(int did, String name, double discountPercent, DateTimeFormatter startDate, DateTimeFormatter endDate) {
        Discount dis = new Discount(did, name, discountPercent, startDate, endDate);
    }
}
