package BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the discount controller
 * All the discounts are in Hash Map that contain did as the key, and the rest of the fields as the value.
 */
public class DiscountController {
    private HashMap<Integer,Discount> discounts;
    private int nextDid;

    public DiscountController() {
        this.discounts = new HashMap<>();
    }

    // Getters
    public ArrayList<Discount> getList() {
        ArrayList<Discount> list = new ArrayList<>(discounts.values());
        return list;
    }

    public Discount getDiscount(int did){
        return discounts.get(did);
    }

    /**
     * Add discounts by object to the hash map
     * @param discount
     */
    public void addDiscount(Discount discount){
        if (discounts.containsKey(discount.getDid())) {
            throw new IllegalArgumentException("Discount ID already exists.");
        } else {
            discounts.put(discount.getDid(), discount);
        }
    }
}
