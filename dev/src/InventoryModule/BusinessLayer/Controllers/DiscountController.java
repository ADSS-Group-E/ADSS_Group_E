package InventoryModule.BusinessLayer.Controllers;

import InventoryModule.BusinessLayer.Discount;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the discount controller
 * All the discounts are in a Hash Map that contains the Discount ID as the key and the rest of the fields as the values.
 */
public class DiscountController {
    private final HashMap<Integer, Discount> discounts;

    public DiscountController() {
        this.discounts = new HashMap<>();
    }

    // Getters
    public ArrayList<Discount> getList() {
        return new ArrayList<>(discounts.values());
    }

    public Discount getDiscount(int did){
        return discounts.get(did);
    }

    /**
     * Add new discount by object to the hash map
     * @param discount The discount to add
     */
    public void addDiscount(Discount discount){
        if (discounts.containsKey(discount.getDid())) {
            throw new IllegalArgumentException("Discount ID already exists.");
        } else {
            discounts.put(discount.getDid(), discount);
        }
    }

    public void removeDiscount(int did) {
        if (discounts.containsKey(did)) {
            discounts.remove(did);
        }
        else {
            throw new IllegalArgumentException("Discount ID does not exist");
        }
    }
}
