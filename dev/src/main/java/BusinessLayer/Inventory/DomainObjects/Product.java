package BusinessLayer.Inventory.DomainObjects;

import DataAccessLayer.Inventory.DataAccessObjects.ItemGroupDAO;
import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.ItemGroupDTO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class represents Products.
 * Each product has a unique Product ID and other details like name, location, price, category and more.
 */
public class Product extends DomainObject{
    private final String name;
    private final String storageLocation;
    private final String storeLocation;
    private final String manufacturer;
    private final double sellingPrice;
    private final int minAmount;
    private final Category category;
    private HashMap<Integer, ItemGroup> storage;
    private HashMap<Integer, ItemGroup> store;
    private Discount discount;

    private final ProductDAO productDAO;
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

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public Category getCategory() {
        return category;
    }

    public Discount getDiscount() {
        return discount;
    }



    // Constructors
    public Product(int pid, String name, String storageLocation, String storeLocation, String manufacturer, double buyingPrice, double sellingPrice, int minAmount, Category category) {
        super(pid);
        this.name = name;
        this.storageLocation = storageLocation;
        this.storeLocation = storeLocation;
        this.manufacturer = manufacturer;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
        this.category = category;
        this.storage = new HashMap<>();
        this.store = new HashMap<>();
        this.discount = null;

        this.productDAO = new ProductDAO();
        this.itemGroupDAO = new ItemGroupDAO();
    }

    public Product(ProductDTO other, Category category) {
        super(other.getPid());
        this.name = other.getName();
        this.storageLocation = other.getStorageLocation();
        this.storeLocation = other.getStoreLocation();
        this.manufacturer = other.getManufacturer();
        this.sellingPrice = other.getSellingPrice();
        this.minAmount = other.getMinAmount();
        this.storage = new HashMap<>();
        this.store = new HashMap<>();
        this.category = category;
        this.discount = null;

        this.productDAO = new ProductDAO();
        this.itemGroupDAO = new ItemGroupDAO();
    }

    public Product(ProductDTO other, Category category, Discount discount) {
        super(other.getPid());
        this.name = other.getName();
        this.storageLocation = other.getStorageLocation();
        this.storeLocation = other.getStoreLocation();
        this.manufacturer = other.getManufacturer();
        this.sellingPrice = other.getSellingPrice();
        this.minAmount = other.getMinAmount();
        this.storage = new HashMap<>();
        this.store = new HashMap<>();
        this.category = category;
        this.discount = discount;

        this.productDAO = new ProductDAO();
        this.itemGroupDAO = new ItemGroupDAO();
    }

    // Adders
    public void addItemGroupToStore(ItemGroup itemGroup){
        ItemGroupDTO itemGroupDTO = new ItemGroupDTO(itemGroup);
        itemGroupDTO.setLocation(ItemGroupDTO.Location.STORE);
        itemGroupDTO.setPid(this.id);
        int id = itemGroupDAO.insertWithAI(itemGroupDTO);
        if (id != -1)
            store.put(id, itemGroup);
    }

    public void addItemGroupToStorage(ItemGroup itemGroup){
        ItemGroupDTO itemGroupDTO = new ItemGroupDTO(itemGroup);
        itemGroupDTO.setLocation(ItemGroupDTO.Location.STORAGE);
        itemGroupDTO.setPid(this.id);
        int id = itemGroupDAO.insertWithAI(itemGroupDTO);
        if (id != -1)
            storage.put(id, itemGroup);
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
        productDAO.update(new ProductDTO(this));

    }

    // More getters
    public double getSellingPriceAfterDiscount(){
        if (discount !=null && discount.discountValid()){
            return (1- discount.getDiscountPercent()) * sellingPrice;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.sellingPrice, sellingPrice) == 0 && minAmount == product.minAmount && name.equals(product.name) && storageLocation.equals(product.storageLocation) && storeLocation.equals(product.storeLocation) && manufacturer.equals(product.manufacturer) && category.equals(product.category) && storage.equals(product.storage) && store.equals(product.store) && Objects.equals(discount, product.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, storageLocation, storeLocation, manufacturer, sellingPrice, minAmount, category, storage, store, discount);
    }
}
