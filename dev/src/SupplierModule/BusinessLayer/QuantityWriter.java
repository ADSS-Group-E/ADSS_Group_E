package SupplierModule.BusinessLayer;

import java.util.Dictionary;
import java.util.Enumeration;

class QuantityWriter {
    private int[][] discount;
    private double regularCostumerDiscount;
    private double minPriceDiscount;

    QuantityWriter(int[][] discount, double regularCostumerDiscount, double minPriceDiscount) {
        this.discount = discount;
        this.regularCostumerDiscount = regularCostumerDiscount;
        this.minPriceDiscount = minPriceDiscount;
    }
}

