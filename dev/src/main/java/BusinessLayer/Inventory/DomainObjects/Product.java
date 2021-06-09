package BusinessLayer.Inventory.DomainObjects;

import DataAccessLayer.Inventory.DataAccessObjects.ItemGroupDAO;
import PresentationLayer.Inventory.DataTransferObjects.ItemGroupDTO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents Products.
 * Each product has a unique Product ID and other details like name, location, price, category and more.
 */
public class Product extends DomainObject{
    private final String name;
    private final String storageLocation;
    private final String storeLocation;
    private final String manufacturer;
    private final double buyingPrice;
    private final double sellingPrice;
    private final int minAmount;
    private final Category category;
    private HashMap<Integer, ItemGroup> storage;
    private HashMap<Integer, ItemGroup> store;
    private Discount buyingDiscount;
    private Discount sellingDiscount;

    private final ItemGroupDAO itemGroupDAO;

    // Getters
    public String getName() {
        return name;
    }

    public int getAmountInStorage(){ return getAmount(storage); }

    public int getAmountInStore(){
        return getAmount(store);
    }

    private int getAmount(HashMap<Integer, ItemGroup> place){
        int totalAmount = 0;
        for (ItemGroup itemGroup:
            place.values()) {
            totalAmount += itemGroup.getQuantity();
        }
        return totalAmount;
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

    public Discount getBuyingDiscount() {
        return buyingDiscount;
    }

    public Discount getSellingDiscount() {
        return sellingDiscount;
    }



    // Constructors
    public Product(int pid, String name, String storageLocation, String storeLocation, String manufacturer, double buyingPrice, double sellingPrice, int minAmount, Category category) {
        super(pid);
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
        this.sellingDiscount = null;

        this.itemGroupDAO = new ItemGroupDAO();
    }

    public Product(ProductDTO other, Category category) {
        super(other.getPid());
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

        this.itemGroupDAO = new ItemGroupDAO();
    }

    // Adders
    public void addItemGroupToStore(ItemGroup itemGroup){
        ItemGroupDTO itemGroupDTO = new ItemGroupDTO(itemGroup);
        itemGroupDTO.setLocation(ItemGroupDTO.Location.STORE);
        itemGroupDTO.setPid(this.id);
        int id = itemGroupDAO.insert(itemGroupDTO);
        if (id != -1)
            store.put(id, itemGroup);
    }

    public void addItemGroupToStorage(ItemGroup itemGroup){
        ItemGroupDTO itemGroupDTO = new ItemGroupDTO(itemGroup);
        itemGroupDTO.setLocation(ItemGroupDTO.Location.STORAGE);
        itemGroupDTO.setPid(this.id);
        int id = itemGroupDAO.insert(itemGroupDTO);
        if (id != -1)
            storage.put(id, itemGroup);
    }

    // Setters
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

    // More getters
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


    public ArrayList<ItemGroup> getExpiredItems(){
        ArrayList<ItemGroup> expiredItemGroups = new ArrayList<>();

        store.values().forEach((itemGroup)->{
            if (itemGroup.isExpired()){
                expiredItemGroups.add(itemGroup);
            }
        });

        storage.values().forEach((itemGroup)->{
            if (itemGroup.isExpired()){
                expiredItemGroups.add(itemGroup);
            }
        });

        return expiredItemGroups;
    }

    // Remover
    public void removeItem(int id) {
        storage.remove(id);
        store.remove(id);
    }



    public void loadStore (HashMap<Integer, ItemGroup> store){
        this.store = store;
    }

    public void loadStorage (HashMap<Integer, ItemGroup> storage){
        this.storage = storage;
    }
}
