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

    public int calcPrice(int price) {
        int maxPercent = 0;
        if (price < getMinPriceDiscount())
            return price;
        for (DiscountStep step : getDiscounts().values()) {
            if (step.getPercentage() > maxPercent && price <= step.getStepPrice())
                maxPercent = step.getPercentage();
        }
        return price * (1 - maxPercent / 100) * (1 - getRegularCostumerDiscount() / 100);

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

    public void addDiscount(DiscountStep discountStep) {
        DiscountStepDTO discountStepDTO  = new DiscountStepDTO(discountStep);
        discountStepDTO.setQuantityWriterId(this.getId());

        int id = discountStepDAO.insertWithAI(discountStepDTO);
        if (id != -1){
            discountStep.setId(id);
            discounts.put(discountStep.getId(), discountStep);
        }
    }
}