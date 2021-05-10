package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.Discount;
import BusinessLayer.Inventory.Product;
import DataAccessLayer.Inventory.DataAccessObjects.CategoryDAO;
import DataAccessLayer.Inventory.DataAccessObjects.DiscountDAO;
import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;
import PresentationLayer.Inventory.DataTransferObjects.DiscountDTO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the discount controller
 * All the discounts are in a Hash Map that contains the Discount ID as the key and the rest of the fields as the values.
 */
public class DiscountController {
    private final HashMap<Integer, Discount> discounts;
    private final DiscountDAO discountDAO;
    private final ProductController pCont;
    private boolean loadedAll = false;

    public DiscountController(ProductController pCont) {
        this.discounts = new HashMap<>();
        discountDAO = new DiscountDAO();
        this.pCont = pCont;
    }

    // Getters
    public ArrayList<Discount> getList() {
        if (loadedAll)
            return new ArrayList<>(discounts.values());
        // Load not yet loaded products from database
        ArrayList<DiscountDTO> discountDTOS = discountDAO.selectAll();
        for (DiscountDTO discountDTO:
                discountDTOS) {
            if (!discounts.containsKey(discountDTO.getDid())){
                Discount discount;
                ArrayList<Product> products = new ArrayList<>();
                if (discountDTO.getPids() != null)
                    for (Integer pid:
                            discountDTO.getPids()) {
                        products.add(pCont.getProduct(pid));
                    }

                if (discountDTO.getType().equals("Buying"))
                    discount = Discount.DiscountForBuying(discountDTO.getDid(), discountDTO.getName(), discountDTO.getDiscountPercent(), discountDTO.getStartDate(), discountDTO.getEndDate(), products);
                else
                    discount = Discount.DiscountForSelling(discountDTO.getDid(), discountDTO.getName(), discountDTO.getDiscountPercent(), discountDTO.getStartDate(), discountDTO.getEndDate(), products);
                discounts.put(discount.getDid(),discount);
            }
        }
        loadedAll = true;
        return new ArrayList<>(discounts.values());
    }


    public Discount getDiscount(int did){
        if (discounts.containsKey(did)){
            return discounts.get(did);
        }
        else{
            // Not in business layer, try to lazy load
            DiscountDTO discountDTO = discountDAO.get(did);
            if (discountDTO == null){
                System.err.println( "Discount id " + did + " does not exist.");
                return null;
            }
            ArrayList<Product> products = new ArrayList<>();
            if (discountDTO.getPids() != null)
                for (Integer pid:
                        discountDTO.getPids()) {
                    products.add(pCont.getProduct(pid));
                }
            Discount discount;
            if (discountDTO.getType().equals("Buying"))
                discount = Discount.DiscountForBuying(discountDTO.getDid(), discountDTO.getName(), discountDTO.getDiscountPercent(), discountDTO.getStartDate(), discountDTO.getEndDate(), products);
            else
                discount = Discount.DiscountForSelling(discountDTO.getDid(), discountDTO.getName(), discountDTO.getDiscountPercent(), discountDTO.getStartDate(), discountDTO.getEndDate(), products);
            discounts.put(discount.getDid(),discount);

            return discount;
        }
    }

    /**
     * Add new discount by object to the hash map
     * @param discount The discount to add
     */
    public void addDiscount(Discount discount){
        discountDAO.insert(new DiscountDTO(discount));
        discounts.put(discount.getDid(),discount);
    }

    public void removeDiscount(int did) {
        discountDAO.delete(did);
        discounts.remove(did);
    }
}
