package SupplierModule.BusinessLayer;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

class QuantityWriter {
    private HashMap<Integer, Integer> discount;
    private double regularCostumerDiscount;
    private double minPriceDiscount;

    QuantityWriter(HashMap<Integer, Integer> discount, double regularCostumerDiscount, double minPriceDiscount) {
        this.discount = discount;
        this.regularCostumerDiscount = regularCostumerDiscount;
        this.minPriceDiscount = minPriceDiscount;
    }

    public int calcPrice(int price) {
        int maxStep = 0;
        if (price < minPriceDiscount)
            return price;
        for (Integer i : discount.keySet()) {
            if (discount.get(i) > maxStep && i <= price)
                maxStep = discount.get(i);
        }
        return (int) (price * (1 - maxStep / 100) * (1 - regularCostumerDiscount/100));
    }
}