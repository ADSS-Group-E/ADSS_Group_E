package BusinessLayer.Inventory;

import PresentationLayer.Inventory.DataTransferObjects.DiscountDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class represents discounts for products.
 * Discounts Can be applied for specific products and/or categories.
 */
public class Discount extends DomainObject{
    private final String name;
    private final double discountPercent;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final ArrayList<Product> products;
    private String type;

    // Getters

    public double getDiscountPercent() {
        return discountPercent;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public String getType() {
        return type;
    }

    private Discount(int did, String name, double discountPercent, LocalDateTime startDate, LocalDateTime endDate, ArrayList<Product> products) {
        super(did);
        this.name = name;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.products = products;
    }

    /**
     * This function creates a discount for selling products
     * @param did discount ID
     * @param name Name
     * @param discountPercent Discount percent
     * @param startDate Start date
     * @param endDate End date
     * @param products Products to apply to
     * @return the discount
     */
    public static Discount DiscountForSelling(int did, String name, double discountPercent, LocalDateTime startDate, LocalDateTime endDate, ArrayList<Product> products){
        Discount discount = new Discount(did, name, discountPercent, startDate, endDate, products);
        products.forEach((product)-> product.setSellingDiscount(discount));
        discount.type = "Selling";
        return discount;
    }

    /**
     * This function creates a discount for buying products
     * @param did discount ID
     * @param name Name
     * @param discountPercent Discount percent
     * @param startDate Start date
     * @param endDate End date
     * @param products Products to apply to
     * @return the discount
     */
    public static Discount DiscountForBuying(int did, String name, double discountPercent, LocalDateTime startDate, LocalDateTime endDate, ArrayList<Product> products){
        Discount discount = new Discount(did, name, discountPercent, startDate, endDate, products);
        products.forEach((product)-> product.setBuyingDiscount(discount));
        discount.type = "Buying";
        return discount;
    }

    public static Discount DiscountForBuying(DiscountDTO discountDTO, ArrayList<Product> products){
        return DiscountForBuying(discountDTO.getDid(),discountDTO.getName(),discountDTO.getDiscountPercent(),discountDTO.getStartDate(),discountDTO.getEndDate(),products);
    }

    public static Discount DiscountForSelling(DiscountDTO discountDTO, ArrayList<Product> products){
        return DiscountForSelling(discountDTO.getDid(),discountDTO.getName(),discountDTO.getDiscountPercent(),discountDTO.getStartDate(),discountDTO.getEndDate(),products);
    }

    /**
     * This function removes a specific product from a discount.
     * @param product the product ID
     */
    public void removeProductFromDiscount(Product product){
        products.removeIf((x)->(x==product));
    }

    /**
     * this function checks if the dates are valid
     * @return true if the discount applies currently, false otherwise
     */
    public boolean discountValid(){
        return (startDate.isBefore(LocalDateTime.now()) && endDate.isAfter(LocalDateTime.now()));
    }

}
