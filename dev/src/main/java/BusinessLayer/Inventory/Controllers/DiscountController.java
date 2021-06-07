package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.Discount;
import BusinessLayer.Inventory.DomainObject;
import BusinessLayer.Inventory.Product;
import DataAccessLayer.Inventory.DataAccessObjects.CategoryDAO;
import DataAccessLayer.Inventory.DataAccessObjects.DiscountDAO;
import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.DiscountDTO;

import java.util.ArrayList;
import java.util.HashMap;

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

        ArrayList<Product> products = new ArrayList<>();
        if (discountDTO.getPids() != null)
            for (Integer pid:
                    discountDTO.getPids()) {
                products.add(pCont.getProduct(pid));
            }

        if (discountDTO.getType().equals("Buying"))
            return Discount.DiscountForBuying(discountDTO.getDid(), discountDTO.getName(), discountDTO.getDiscountPercent(), discountDTO.getStartDate(), discountDTO.getEndDate(), products);
        else
            return Discount.DiscountForSelling(discountDTO.getDid(), discountDTO.getName(), discountDTO.getDiscountPercent(), discountDTO.getStartDate(), discountDTO.getEndDate(), products);
    }

    @Override
    protected DataTransferObject buildDtoFromDomainObject(DomainObject domainObject) {
        return null;
    }


    public Discount getDiscount(int did){
        return (Discount) get(did);
    }

    /**
     * Add new discount by object to the hash map
     * @param discount The discount to add
     */
    public void addDiscount(Discount discount){
        add(discount);
    }

    public void removeDiscount(int did) {
        remove(did);
    }
}
