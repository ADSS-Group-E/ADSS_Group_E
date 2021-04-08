package BusinessLayer;

import PresentationLayer.ProductDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private int pid;
    private String name;
    private String storageLocation;
    private String storeLocation;
    private String manufacturer;
    private double buyingPrice;
    private double sellingPrice;
    private int minAmount;
    private Category category;
    private HashMap<Integer, Item> storage;
    private HashMap<Integer, Item> store;
    private Discount buyingDiscount;
    private Discount sellingDiscount;

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

    public Category getCategory() {
        return category;
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
        this.storage = new HashMap<>();
        this.store = new HashMap<>();
        this.buyingDiscount = null;
    }

    public Product(ProductDTO other, Category category) {
        this.pid = other.getPid();
        this.name = other.getName();
        this.storageLocation = other.getStorageLocation();
        this.storeLocation = other.getStoreLocation();
        this.manufacturer = other.getManufacturer();
        this.buyingPrice = other.getBuyingPrice();
        this.sellingPrice = other.getSellingPrice();
        this.minAmount = other.getMinAmount();
        this.storage = new HashMap<>();
        this.store = new HashMap<>();
        this.category = category;
    }

    // TODO check id before entering new item
    public void addItemToStore(int id, LocalDateTime expiration){
        store.put(id, new Item(id,expiration));
    }

    public void addItemToStorage(int id, LocalDateTime expiration){
        storage.put(id, new Item(id,expiration));
    }

    public void setBuyingDiscount(Discount buyingDiscount) {
        // Remove the listing from the discount object of the previous discount
        if (this.buyingDiscount != null){
            this.buyingDiscount.removeProductFromDiscount(this);
        }
        this.buyingDiscount = buyingDiscount;
    }

    public void setSellingDiscount(Discount sellingDiscount) {
        // Remove the listing from the discount object of the previous discount
        if (this.sellingDiscount != null){
            this.sellingDiscount.removeProductFromDiscount(this);
        }
        this.sellingDiscount = sellingDiscount;
    }

    public double getBuyingPriceAfterDiscount(){
        if (buyingDiscount !=null && buyingDiscount.discountValid()){
            return (1- buyingDiscount.getDiscountPercent()) * buyingPrice;
        }
        else{
            return buyingPrice;
        }
    }

    public double getSellingPriceAfterDiscount(){
        if (sellingDiscount !=null && sellingDiscount.discountValid()){
            return (1- sellingDiscount.getDiscountPercent()) * sellingPrice;
        }
        else{
            return sellingPrice;
        }
    }

    // Check through the category's super-categories as well.
    public boolean isInCategory(Category checkCategory){
        Category applicableCategory = category;
        // n is just there to prevent an endless loop if a mistake was made and a category was set as a super-category of one of its descendants
        int n = 0;
        while (applicableCategory != null && n != 10){
            if (applicableCategory == checkCategory){
                return true;
            }
            applicableCategory = applicableCategory.getSuperCategory();
            n++;
        }
        return false;
    }


    public ArrayList<Item> getExpiredItems(){
        ArrayList<Item> expiredItems = new ArrayList<>();

        store.values().forEach((item)->{
            if (item.isExpired()){
                expiredItems.add(item);
            }
        });

        storage.values().forEach((item)->{
            if (item.isExpired()){
                expiredItems.add(item);
            }
        });

        return expiredItems;
    }

    public void removeItem(int id) {
        storage.remove(id);
        store.remove(id);
    }
}
