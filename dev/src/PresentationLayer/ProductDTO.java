package PresentationLayer;

import BusinessLayer.Product;

public class ProductDTO {
    private int pid;
    private String name;
    private String storageLocation;
    private String storeLocation;
    private int amountInStorage;
    private int amountInStore;
    private String manufacturer;
    private double buyingPrice;
    private double buyingPriceAfterDiscount;
    private double sellingPrice;
    private double sellingPriceAfterDiscount;
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

    public ProductDTO(int pid, String name, String storageLocation, String storeLocation, String manufacturer, double buyingPrice, double sellingPrice, int minAmount) {
        this.pid = pid;
        this.name = name;
        this.storageLocation = storageLocation;
        this.storeLocation = storeLocation;
        this.amountInStorage = 0;
        this.amountInStore = 0;
        this.manufacturer = manufacturer;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
        this.buyingPriceAfterDiscount = buyingPrice;
        this.sellingPriceAfterDiscount = sellingPrice;
    }

    public ProductDTO(Product other) {
        this.pid = other.getPid();
        this.name = other.getName();
        this.storageLocation = other.getStorageLocation();
        this.storeLocation = other.getStoreLocation();
        this.amountInStorage = other.getAmountInStorage();
        this.amountInStore = other.getAmountInStore();
        this.manufacturer = other.getManufacturer();
        this.buyingPrice = other.getBuyingPrice();
        this.buyingPriceAfterDiscount = other.getBuyingPriceAfterDiscount();
        this.sellingPrice = other.getSellingPrice();
        this.sellingPriceAfterDiscount = other.getSellingPriceAfterDiscount();
        this.minAmount = other.getMinAmount();
    }

    public String toString(){
        return  "PID:                           " + pid + "\n" +
                "Name:                          " + name + "\n" +
                "Storage Location:              " + storageLocation + "\n" +
                "Store Location:                " + storeLocation + "\n" +
                "Amount In Storage:             " + amountInStorage + "\n" +
                "Amount In Store:               " + amountInStore + "\n" +
                "Manufacturer:                  " + manufacturer + "\n" +
                "Buying Price:                  " + buyingPrice + "\n" +
                "Buying Price after Discount:   " + buyingPriceAfterDiscount + "\n" +
                "Selling Price:                 " + sellingPrice + "\n" +
                "Selling Price after Discount:  " + sellingPriceAfterDiscount + "\n" +
                "Min Amount:                    " + minAmount;
    }
}
