package BusinessLayer.Supplier;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import PresentationLayer.Supplier.DataTransferObjects.DiscountStepDTO;
import PresentationLayer.Supplier.DataTransferObjects.QuantityWriterDTO;

import java.util.ArrayList;

public class QuantityWriter extends DomainObject {
    private final int companyNumber;
    private final int regularCostumerDiscount;
    private final int minPriceDiscount;
    private final ArrayList<DiscountStepDTO> discounts; // TODO

    public QuantityWriter(int id, int companyNumber, int regularCostumerDiscount, int minPriceDiscount, ArrayList<DiscountStepDTO> discounts) {
        super(id);
        this.companyNumber = companyNumber;
        this.regularCostumerDiscount = regularCostumerDiscount;
        this.minPriceDiscount = minPriceDiscount;
        this.discounts = discounts;
    }

    public QuantityWriter(int companyNumber, int regularCostumerDiscount, int minPriceDiscount, ArrayList<DiscountStepDTO> discounts) {
        super(-1);
        this.companyNumber = companyNumber;
        this.regularCostumerDiscount = regularCostumerDiscount;
        this.minPriceDiscount = minPriceDiscount;
        this.discounts = discounts;
    }

    public QuantityWriter(QuantityWriterDTO other){
        super(other.getId());
        this.companyNumber = other.getCompanyNumber();
        this.regularCostumerDiscount = other.getRegularCostumerDiscount();
        this.minPriceDiscount = other.getMinPriceDiscount();
        this.discounts = null; //TODO
    }

    int calcPrice(QuantityWriterDTO quantityWriterDTO, int price) {
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

    public int getCompanyNumber() {
        return companyNumber;
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