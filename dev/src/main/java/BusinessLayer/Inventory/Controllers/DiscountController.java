package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.DomainObjects.Discount;
import BusinessLayer.Inventory.DomainObjects.DomainObject;
import BusinessLayer.Inventory.DomainObjects.Product;
import DataAccessLayer.Inventory.DataAccessObjects.DiscountDAO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.DiscountDTO;

import java.util.ArrayList;

/**
 * This class represents the discount controller
 * All the discounts are in a Hash Map that contains the Discount ID as the key and the rest of the fields as the values.
 */
public class DiscountController extends DomainController{
    private final ProductController pCont;

    public DiscountController(ProductController pCont) {
        super(new DiscountDAO());
        this.pCont = pCont;
    }

    // Internal for testing
    protected DiscountController(ProductController pCont, DiscountDAO discountDAO) {
        super(discountDAO);
        this.pCont = pCont;
    }

    // Getters
    public ArrayList<Discount> getAllDiscounts() {
        ArrayList<Discount> discounts = new ArrayList<>();
        ArrayList<DomainObject> domainObjects = getList();

        for (DomainObject domainObject:
                domainObjects) {
            discounts.add((Discount) domainObject);
        }

        return discounts;
    }

    @Override
    protected DomainObject buildDomainObjectFromDto(DataTransferObject dataTransferObject) {
        DiscountDTO discountDTO = (DiscountDTO) dataTransferObject;
        return new Discount(discountDTO);
    }

    @Override
    protected DataTransferObject buildDtoFromDomainObject(DomainObject domainObject) {
        Discount discount = (Discount) domainObject;
        return new DiscountDTO(discount, pCont.getAllProductsWithDiscount(discount));
    }


    public Discount getDiscount(int did){
        return (Discount) get(did);
    }

    /**
     * Add new discount by object to the hash map
     * @param discount The discount to add
     */
    public void addDiscount(Discount discount, ArrayList<Product> products){
        // Apply the discount of each of the applicable products.
        products.forEach((product)-> product.setDiscount(discount));
        add(discount);
    }

    public void removeDiscount(int did) {
        remove(did);
    }
}
