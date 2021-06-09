package PresentationLayer.Inventory.DataTransferObjects;

import BusinessLayer.Inventory.DomainObjects.Product;

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
    private final double sellingPrice;
    private final double sellingPriceAfterDiscount;
    private final int minAmount;
    private final int categoryId;
    private final int sellingDiscountID;

    // Getters
    public int getPid() {
        return id;
    }

    public String getName() {
        return name;
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

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public Integer getSellingDiscountID() {
        if (sellingDiscountID == -1)
            return null;
        return sellingDiscountID;
    }

    // Constructors
    public ProductDTO(int pid, String name, String storageLocation, String storeLocation, String manufacturer, double sellingPrice, int minAmount, int categoryId, int sellingDiscountID) {
        super(pid);
        this.name = name;
        this.storageLocation = storageLocation;
        this.storeLocation = storeLocation;
        this.amountInStorage = 0;
        this.amountInStore = 0;
        this.manufacturer = manufacturer;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
        this.sellingPriceAfterDiscount = sellingPrice;
        this.categoryId = categoryId;
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
        this.sellingPrice = other.getSellingPrice();
        this.sellingPriceAfterDiscount = other.getSellingPriceAfterDiscount();
        this.minAmount = other.getMinAmount();
        this.categoryId = other.getCategory().getId();
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
                "Selling Price:                 " + sellingPrice + "\n" +
                "Selling Price after Discount:  " + sellingPriceAfterDiscount + "\n" +
                "Min Amount:                    " + minAmount + "\n" +
                "Category ID:                   " + categoryId + "\n" +
                "Discount ID:                   " + sellingDiscountID ;
    }
}
