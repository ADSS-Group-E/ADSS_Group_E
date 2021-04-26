package BusinessLayer.Supplier;

import java.util.HashMap;

public class QuantityWriter {
    private HashMap<Integer, Integer> discount;
    private double regularCostumerDiscount;
    private double minPriceDiscount;

    public QuantityWriter(HashMap<Integer, Integer> discount, double regularCostumerDiscount, double minPriceDiscount) {
        this.discount = discount;
        this.regularCostumerDiscount = regularCostumerDiscount;
        this.minPriceDiscount = minPriceDiscount;
    }

    int calcPrice(int price) {
        //calculates the price
        int maxStep = 0;
        if (price < minPriceDiscount)
            return price;
        for (Integer i : discount.keySet()) {
            if (discount.get(i) > maxStep && i <= price)
                maxStep = discount.get(i);
        }
        return (int) (price * (1 - maxStep / 100) * (1 - regularCostumerDiscount/100));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof QuantityWriter){
            QuantityWriter ob = (QuantityWriter) obj;
            return ob.regularCostumerDiscount == this.regularCostumerDiscount
                    & ob.minPriceDiscount == this.minPriceDiscount
                    & ob.discount.equals(this.discount);
        }
        else return false;
    }
}