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
}