package BusinessLayer.Supplier.DomainObjects;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import PresentationLayer.Supplier.DataTransferObjects.DiscountStepDTO;
import PresentationLayer.Supplier.DataTransferObjects.QuantityWriterDTO;

import java.util.ArrayList;

public class QuantityWriter extends DomainObject {
    private final int regularCostumerDiscount;
    private final int minPriceDiscount;
    private final ArrayList<DiscountStep> discounts; // TODO

    public QuantityWriter(int id, int regularCostumerDiscount, int minPriceDiscount, ArrayList<DiscountStep> discounts) {
        super(id);
        this.regularCostumerDiscount = regularCostumerDiscount;
        this.minPriceDiscount = minPriceDiscount;
        this.discounts = discounts;
    }

    public QuantityWriter(int regularCostumerDiscount, int minPriceDiscount, ArrayList<DiscountStep> discounts) {
        super(-1);
        this.regularCostumerDiscount = regularCostumerDiscount;
        this.minPriceDiscount = minPriceDiscount;
        this.discounts = discounts;
    }

    public QuantityWriter(QuantityWriterDTO other){
        super(other.getId());
        this.regularCostumerDiscount = other.getRegularCostumerDiscount();
        this.minPriceDiscount = other.getMinPriceDiscount();
        this.discounts = null; //TODO
    }

    public int calcPrice(QuantityWriterDTO quantityWriterDTO, int price) {
        if (quantityWriterDTO != null) {
            //calculates the price
            int maxPrecenet = 0;
            if (price < quantityWriterDTO.getMinPriceDiscount())
                return price;
            for (DiscountStepDTO step : quantityWriterDTO.getDiscounts()) {
                if (step.getPercentage() > maxPrecenet && price <= step.getStepPrice())
                    maxPrecenet = step.getPercentage();
            }
            return price * (1 - maxPrecenet / 100) * (1 - quantityWriterDTO.getRegularCostumerDiscount() / 100);
        }
        return price;
    }

    public int getRegularCostumerDiscount() {
        return regularCostumerDiscount;
    }

    public int getMinPriceDiscount() {
        return minPriceDiscount;
    }

    public ArrayList<DiscountStepDTO> getDiscounts() {
        return discounts;
    }
}