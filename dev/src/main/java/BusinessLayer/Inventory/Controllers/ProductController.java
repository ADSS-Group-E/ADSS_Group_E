package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.DomainObject;
import BusinessLayer.Inventory.Product;
import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.util.ArrayList;

/**
 * This class represents the product controller.
 * All the products are in a Hash Map that contains the Product ID as the key and the rest of the fields as the value.
 */
public class ProductController extends DomainController{
    private final CategoryController cCont;
    // Indicates all products have already been loaded so don't need to call selectAll again
    private boolean loadedAll = false;

    public ProductController(CategoryController cCont) {
        super(new ProductDAO("jdbc:sqlite::resource:module.db"));
        this.cCont = cCont;
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

    @Override
    protected DomainObject buildDomainObjectFromDto(DataTransferObject dataTransferObject) {
        ProductDTO productDTO = (ProductDTO) dataTransferObject;

        Category category = cCont.getCategory(productDTO.getCategoryId());
        if (category == null){
            return null;
        }

        return new Product(productDTO, category);
    }

    @Override
    protected DataTransferObject buildDtoFromDomainObject(DomainObject domainObject) {
        return new ProductDTO((Product) domainObject);
    }
}