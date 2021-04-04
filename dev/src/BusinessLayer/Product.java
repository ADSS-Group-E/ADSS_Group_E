package BusinessLayer;

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
}
