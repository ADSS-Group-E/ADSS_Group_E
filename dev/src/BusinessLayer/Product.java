package BusinessLayer;

import PresentationLayer.ProductDTO;
import dev.src.BusinessLayer.Discount;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Product {
    private int pid;
    private String name;
    private String storageLocation;
    private String storeLocation;
    private String manufacturer;
    private double buyingPrice;
    private double sellingPrice;
    // TODO NEED PRICE AFTER DISCOUNT? and discount field
    private int minAmount;
    private Category category;
    private Category subCategory;
    private Category subSubCategory;
    private ArrayList<Item> storage;
    private ArrayList<Item> store;

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public int getAmountInStorage(){
        return storage.size();
    }

    public int getAmountInStore(){
        return store.size();
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public String getStoreLocation() {
        return storeLocation;
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

    public Product(int pid, String name, String storageLocation, String storeLocation, String manufacturer, double buyingPrice, double sellingPrice, int minAmount, BusinessLayer.Category category, BusinessLayer.Category subCategory, BusinessLayer.Category subSubCategory) {
        this.pid = pid;
        this.name = name;
        this.storageLocation = storageLocation;
        this.storeLocation = storeLocation;
        this.manufacturer = manufacturer;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
        this.category = category;
        this.subCategory = subCategory;
        this.subSubCategory = subSubCategory;
        this.storage = new ArrayList<>();
        this.store = new ArrayList<>();
    }

    public Product(ProductDTO other) {
        this.pid = other.getPid();
        this.name = other.getName();
        this.storageLocation = other.getStorageLocation();
        this.storeLocation = other.getStoreLocation();
        this.manufacturer = other.getManufacturer();
        this.buyingPrice = other.getBuyingPrice();
        this.sellingPrice = other.getSellingPrice();
        this.minAmount = other.getMinAmount();
        this.storage = new ArrayList<>();
        this.store = new ArrayList<>();
        // TODO add categories to ProductDTO (?)
    }

    // TODO check id before entering new item
    public void addItemToStore(int id, LocalDateTime expiration){
        store.add(new Item(id,expiration));
    }

    public void addItemToStorage(int id, LocalDateTime expiration){
        storage.add(new Item(id,expiration));
    }

    public void setDiscount(int did, String name, double discountPercent, LocalDateTime startDate, LocalDateTime endDate) {
        Discount dis = new Discount(did, name, discountPercent, startDate, endDate);
    }
}
