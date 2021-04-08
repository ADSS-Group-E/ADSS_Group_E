package BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscountController {
    private HashMap<Integer,Discount> discounts;
    private int nextDid;

    public DiscountController() {
        this.discounts = new HashMap<>();
    }

    public Discount getDiscount(int did){
        return discounts.get(did);
    }

    public void addDiscount(Discount discount){
        if (discounts.containsKey(discount.getDid())) {
            throw new IllegalArgumentException("Discount ID already exist");
        } else {
            discounts.put(discount.getDid(), discount);
        }
    }

    public ArrayList<Discount> getList() {
        ArrayList<Discount> list = new ArrayList<>(discounts.values());
        return list;
    }
}
