package PresentationLayer.Supplier.DataTransferObjects;

import BusinessLayer.Supplier.DomainObjects.QuantityWriter;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

public class QuantityWriterDTO extends DataTransferObject {
    private int companyNumber;
    private final int regularCostumerDiscount;
    private final int minPriceDiscount;

    public QuantityWriterDTO(int id, int companyNumber, int regularCostumerDiscount, int minPriceDiscount) {
        super(id);
        this.companyNumber = companyNumber;
        this.regularCostumerDiscount = regularCostumerDiscount;
        this.minPriceDiscount = minPriceDiscount;
    }

    public QuantityWriterDTO(int companyNumber, int regularCostumerDiscount, int minPriceDiscount) {
        super(-1);
        this.companyNumber = companyNumber;
        this.regularCostumerDiscount = regularCostumerDiscount;
        this.minPriceDiscount = minPriceDiscount;
    }

    public QuantityWriterDTO(QuantityWriter other) {
        super(other.getId());
        this.regularCostumerDiscount = other.getRegularCostumerDiscount();
        this.minPriceDiscount = other.getMinPriceDiscount();
    }

    public void setCompanyNumber(int companyNumber) {
        this.companyNumber = companyNumber;
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

    public void setId(int id) { this.id = id; }
}
