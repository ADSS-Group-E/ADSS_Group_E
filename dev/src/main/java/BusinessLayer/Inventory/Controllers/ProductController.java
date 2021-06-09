package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.DomainObjects.*;
import DataAccessLayer.Inventory.DataAccessObjects.ItemGroupDAO;
import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ItemGroupDTO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the product controller.
 * All the products are in a Hash Map that contains the Product ID as the key and the rest of the fields as the value.
 */
public class ProductController extends DomainController{
    private final CategoryController cCont;
    private final ItemGroupDAO itemGroupDAO;


    public ProductController(CategoryController cCont) {
        super(new ProductDAO("jdbc:sqlite::resource:module.db"));
        this.cCont = cCont;
        itemGroupDAO = new ItemGroupDAO();
    }

    // Internal for testing
    protected ProductController(CategoryController cCont, ProductDAO productDAO, ItemGroupDAO itemGroupDAO) {
        super(productDAO);
        this.cCont = cCont;
        this.itemGroupDAO = itemGroupDAO;
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
            if(product.getSellingDiscount() != null && product.getSellingDiscount().getId() == discount.getId()){
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
        Product product = new Product(productDTO, category);
        product.loadStorage(storage);
        product.loadStore(store);

        return product;
    }

    @Override
    protected DataTransferObject buildDtoFromDomainObject(DomainObject domainObject) {
        return new ProductDTO((Product) domainObject);
    }
}