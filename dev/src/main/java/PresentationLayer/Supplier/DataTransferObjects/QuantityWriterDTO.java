package PresentationLayer.Supplier.DataTransferObjects;

import java.util.ArrayList;
import java.util.HashMap;

public class QuantityWriterDTO {
    private int id;
    private final int companyNumber;
    private final int regularCostumerDiscount;
    private final int minPriceDiscount;
    private final ArrayList<DiscountStepDTO> discounts;

    public QuantityWriterDTO(int id, int companyNumber, int regularCostumerDiscount, int minPriceDiscount, ArrayList<DiscountStepDTO> discounts) {
        this.id = id;
        this.companyNumber = companyNumber;
        this.regularCostumerDiscount = regularCostumerDiscount;
        this.minPriceDiscount = minPriceDiscount;
        this.discounts = discounts;
    }

    public QuantityWriterDTO(int companyNumber, int regularCostumerDiscount, int minPriceDiscount, ArrayList<DiscountStepDTO> discounts) {
        this.id = -1;
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

    public ArrayList<DiscountStepDTO> getDiscounts() { return discounts; }

    public void setId(int id) { this.id = id; }
}
