package PresentationLayer.Inventory.DataTransferObjects;

import BusinessLayer.Inventory.Discount;
import BusinessLayer.Inventory.Product;

/**
 * This class represents the ProductDTO.
 * A DTO is an object that is used to encapsulate data and send it from one subsystem of an application to another.
 */

public class ProductDTO extends DataTransferObject{
    private final String name;
    private final String storageLocation;
    private final String storeLocation;
    private final int amountInStorage;
    private final int amountInStore;
    private final String manufacturer;
    private final double buyingPrice;
    private final double buyingPriceAfterDiscount;
    private final double sellingPrice;
    private final double sellingPriceAfterDiscount;
    private final int minAmount;
    private final int categoryId;
    private final int buyingDiscountID;
    private final int sellingDiscountID;

    // Getters
    public int getPid() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBuyingPriceAfterDiscount() {
        return buyingPriceAfterDiscount;
    }

    public double getSellingPriceAfterDiscount() {
        return sellingPriceAfterDiscount;
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

    public int getCategoryId() {
        return categoryId;
    }

    public Integer getBuyingDiscountID() {
        if (buyingDiscountID == -1)
            return null;
        return buyingDiscountID;
    }

    public Integer getSellingDiscountID() {
        if (sellingDiscountID == -1)
            return null;
        return sellingDiscountID;
    }

    // Constructors
    public ProductDTO(int pid, String name, String storageLocation, String storeLocation, String manufacturer, double buyingPrice, double sellingPrice, int minAmount, int categoryId, int buyingDiscountID, int sellingDiscountID) {
        super(pid);
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
        this.categoryId = categoryId;
        this.buyingDiscountID = buyingDiscountID;
        this.sellingDiscountID = sellingDiscountID;
    }

    public ProductDTO(Product other) {
        super(other.getId());
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
        this.categoryId = other.getCategory().getId();
        if (other.getBuyingDiscount() != null)
            this.buyingDiscountID = other.getBuyingDiscount().getId();
        else
            this.buyingDiscountID = -1;
        if (other.getSellingDiscount() != null)
            this.sellingDiscountID = other.getSellingDiscount().getId();
        else
            this.sellingDiscountID = -1;
    }

    // Print
    public String toString(){
        return  "PID:                           " + id + "\n" +
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
                "Min Amount:                    " + minAmount + "\n" +
                "Category ID:                   " + categoryId;
    }
}
