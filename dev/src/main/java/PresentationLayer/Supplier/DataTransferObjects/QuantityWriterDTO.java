package PresentationLayer.Supplier.DataTransferObjects;

import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

public class QuantityWriterDTO extends DataTransferObject {
    private final int companyNumber;
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
