package BusinessLayer.Supplier.DomainObjects;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import DataAccessLayer.Supplier.DataAccessObjects.DiscountStepDAO;
import PresentationLayer.Supplier.DataTransferObjects.DiscountStepDTO;
import PresentationLayer.Supplier.DataTransferObjects.QuantityWriterDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemGroupDTO;

import java.util.ArrayList;
import java.util.HashMap;

public class QuantityWriter extends DomainObject {
    private final int regularCostumerDiscount;
    private final int minPriceDiscount;
    private HashMap<Integer, DiscountStep> discounts; // TODO

    private boolean loaded = false;
    private final DiscountStepDAO discountStepDAO = new DiscountStepDAO();

    public QuantityWriter(int id, int regularCostumerDiscount, int minPriceDiscount, HashMap<Integer, DiscountStep> discounts) {
        super(id);
        this.regularCostumerDiscount = regularCostumerDiscount;
        this.minPriceDiscount = minPriceDiscount;
        this.discounts = discounts;
    }

    public QuantityWriter(int regularCostumerDiscount, int minPriceDiscount, HashMap<Integer, DiscountStep> discounts) {
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

    public HashMap<Integer, DiscountStep> getDiscounts() {
        return discounts;
    }

    public void loadDiscounts() {
        // Load a product's itemGroups
        if (!loaded){
            ArrayList<DiscountStepDTO> discountStepDTOS = discountStepDAO.selectByQuantityWriter(this.getId());
            HashMap<Integer,DiscountStep> discountSteps = new HashMap<>();

            for (DiscountStepDTO discountStepDTO:
                    discountStepDTOS) {
                DiscountStep discountStep = new DiscountStep(discountStepDTO);
                discountSteps.put(discountStep.getId(), discountStep);
            }

            this.discounts = discountSteps;

            loaded = true;
        }
    }
}