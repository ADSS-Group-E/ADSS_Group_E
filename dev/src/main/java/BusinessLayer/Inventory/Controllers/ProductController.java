package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.DomainObjects.*;
import BusinessLayer.Workers_Transport.DeliveryPackage.Order;
import DataAccessLayer.Inventory.DataAccessObjects.ItemGroupDAO;
import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ItemGroupDTO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class represents the product controller.
 * All the products are in a Hash Map that contains the Product ID as the key and the rest of the fields as the value.
 */
public class ProductController extends DomainController{
    private final CategoryController cCont;
    private DiscountController dCont;
    private final ItemGroupDAO itemGroupDAO;


    public ProductController(CategoryController cCont) {
        super(new ProductDAO("jdbc:sqlite::resource:module.db"));
        this.cCont = cCont;
        itemGroupDAO = new ItemGroupDAO();
    }

    public void setDiscountController(DiscountController dCont) {
        this.dCont = dCont;
    }

    // Internal for testing
    protected ProductController(CategoryController cCont, ProductDAO productDAO, ItemGroupDAO itemGroupDAO) {
        super(productDAO);
        this.cCont = cCont;
        this.itemGroupDAO = itemGroupDAO;
    }

    // Dismissals is a map between a product id and the amount which needs to be dismissed from the order
    public void acceptOrder(Order order, HashMap<Integer, Integer> dismissals){
        HashMap<Product, ItemGroup> itemGroups = new HashMap<>();

        //TODO: Implement OrderItem, getOrderItems, getPid, ItemGroup constructor with OrderItem, Order.accept
        // First iterate through orderItems to get it's corresponding product and create a new itemGroup for it
        HashMap<Integer,Integer> orderItems = new HashMap<>(order.getItems());
        Iterator<Map.Entry<Integer, Integer>> it = orderItems.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> pair = it.next();
            int productId = pair.getKey();
            int quantity = pair.getValue();

            Product product = getProduct(productId);
            if (product == null){
                System.err.println( "acceptOrder couldn't match pid " + productId);
                return;
            }
            // TODO something about price
            double price = product.getSellingPrice() + 10;

            ItemGroup itemGroup = new ItemGroup(quantity, price, LocalDateTime.of(2020,7,13,12,0));

            if (dismissals.containsKey(product.getId())){
                int amountToDismiss = dismissals.get(product.getId());
                if (itemGroup.getQuantity() - amountToDismiss > 0){
                    // Dismissing part of itemgroup
                    itemGroup.setQuantity(itemGroup.getQuantity() - amountToDismiss);
                    dismissals.remove(product.getId());

                    itemGroups.put(product, itemGroup);
                }
                else{
                    // Dismissing whole itemgroup
                    dismissals.put(product.getId(), amountToDismiss - itemGroup.getQuantity());
                }
            }
            else{
                itemGroups.put(product, itemGroup);
            }

            it.remove(); // avoids a ConcurrentModificationException
        }


        // Once every orderItem has been confirmed to have a matching product, add the itemGroups to storage
        Iterator<Map.Entry<Product, ItemGroup>> it2 = itemGroups.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<Product, ItemGroup> pair = it2.next();

            pair.getKey().addItemGroupToStorage(pair.getValue());

            it2.remove(); // avoids a ConcurrentModificationException
        }

        // order.accept();
    }

    // Getter
    public Product getProduct(int pid){
        return (Product) get(pid);
    }

    // Adders
    public void addProduct(Product product){
        add(product);
    }

    // Remover
    public void removeProduct (int pid) {
        remove(pid);
    }

    // More getters
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<DomainObject> domainObjects = getList();

        for (DomainObject domainObject:
             domainObjects) {
            products.add((Product)domainObject);
        }

        return products;
    }

    public ArrayList<Product> getAllProductsOfCategory(Category category){
        ArrayList<Product> productsOfCategory = new ArrayList<>();
        ArrayList<Product> allProducts = getAllProducts();
        allProducts.forEach((product)->{
            if(product.isInCategory(category)){
                productsOfCategory.add(product);
            }
        });
        return productsOfCategory;
    }

    public ArrayList<Product> getAllProductsWithDiscount(Discount discount){
        ArrayList<Product> productsOfDiscount = new ArrayList<>();
        ArrayList<Product> allProducts = getAllProducts();
        allProducts.forEach((product)->{
            if(product.getDiscount() != null && product.getDiscount().getId() == discount.getId()){
                productsOfDiscount.add(product);
            }
        });
        return productsOfDiscount;
    }

    @Override
    protected DomainObject buildDomainObjectFromDto(DataTransferObject dataTransferObject) {
        ProductDTO productDTO = (ProductDTO) dataTransferObject;

        Category category = cCont.getCategory(productDTO.getCategoryId());
        if (category == null){
            return null;
        }

        Discount discount = null;
        if (productDTO.getDiscountID() != null)
            discount = dCont.getDiscount(productDTO.getDiscountID());

        // Load a product's itemGroups
        ArrayList<ItemGroupDTO> itemGroupDTOS = itemGroupDAO.selectByProduct(productDTO.getPid());
        HashMap<Integer,ItemGroup> store = new HashMap<>();
        HashMap<Integer,ItemGroup> storage = new HashMap<>();

        for (ItemGroupDTO itemGroupDTO:
             itemGroupDTOS) {
            if (itemGroupDTO.getLocation() == ItemGroupDTO.Location.STORE){
                store.put(itemGroupDTO.getPid(), new ItemGroup(itemGroupDTO));
            }
            if (itemGroupDTO.getLocation() == ItemGroupDTO.Location.STORAGE){
                storage.put(itemGroupDTO.getPid(), new ItemGroup(itemGroupDTO));
            }
        }
        Product product = new Product(productDTO, category, discount);
        product.loadStorage(storage);
        product.loadStore(store);

        return product;
    }

    @Override
    protected DataTransferObject buildDtoFromDomainObject(DomainObject domainObject) {
        return new ProductDTO((Product) domainObject);
    }
}