package PresentationLayer.Supplier.DataTransferObjects;

import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

import java.util.ArrayList;
import java.util.HashMap;

public class QuantityWriterDTO extends DataTransferObject {
    private final int companyNumber;
    private final int regularCostumerDiscount;
    private final int minPriceDiscount;
    private final ArrayList<DiscountStepDTO> discounts;

    public QuantityWriterDTO(int id, int companyNumber, int regularCostumerDiscount, int minPriceDiscount, ArrayList<DiscountStepDTO> discounts) {
        super(id);
        this.companyNumber = companyNumber;
        this.regularCostumerDiscount = regularCostumerDiscount;
        this.minPriceDiscount = minPriceDiscount;
        this.discounts = discounts;
    }

    public QuantityWriterDTO(int companyNumber, int regularCostumerDiscount, int minPriceDiscount, ArrayList<DiscountStepDTO> discounts) {
        super(-1);
        this.companyNumber = companyNumber;
        this.regularCostumerDiscount = regularCostumerDiscount;
        this.minPriceDiscount = minPriceDiscount;
        this.discounts = discounts;
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
