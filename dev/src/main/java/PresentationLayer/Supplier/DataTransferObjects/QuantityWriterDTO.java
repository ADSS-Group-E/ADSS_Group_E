package PresentationLayer.Supplier.DataTransferObjects;

import java.util.HashMap;

public class QuantityWriterDTO {
    private final int id;
    private final int companyNumber;
    private final int regularCostumerDiscount;
    private final int minPriceDiscount;
    private final HashMap<Integer, DiscountStepDTO> discounts;

    public QuantityWriterDTO(int id, int companyNumber, int regularCostumerDiscount, int minPriceDiscount, HashMap<Integer, DiscountStepDTO> discounts) {
        this.id = id;
        this.companyNumber = companyNumber;
        this.regularCostumerDiscount = regularCostumerDiscount;
        this.minPriceDiscount = minPriceDiscount;
        this.discounts = discounts;
    }

    public int getId() {
        return id;
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

    public HashMap<Integer, DiscountStepDTO> getDiscounts() {
        return discounts;
    }

    public void add(int index, DiscountStepDTO discount) {
        discounts.put(index, discount);
    }
}
