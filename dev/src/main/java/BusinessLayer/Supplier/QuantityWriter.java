package BusinessLayer.Supplier;

import PresentationLayer.Supplier.DataTransferObjects.DiscountStepDTO;
import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.QuantityWriterDTO;

import java.util.HashMap;

public class QuantityWriter {

    int calcPrice(QuantityWriterDTO quantityWriterDTO, int price) {
        if (quantityWriterDTO != null) {
            //calculates the price
            int maxPrecenet = 0;
            if (price < quantityWriterDTO.getMinPriceDiscount())
                return price;
            for (DiscountStepDTO step : quantityWriterDTO.getDiscounts()) {
                if (step.getPrecentage() > maxPrecenet && price <= step.getStepPrice())
                    maxPrecenet = step.getPrecentage();
            }
            return price * (1 - maxPrecenet / 100) * (1 - quantityWriterDTO.getRegularCostumerDiscount() / 100);
        }
        return price;
    }
}