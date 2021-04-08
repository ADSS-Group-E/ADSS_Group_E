package BusinessLayer;

import PresentationLayer.DiscountDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Discount {
    private int did;
    private String name;
    private double discountPercent;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ArrayList<Product> products;
    private String type;

    public int getDid() {
        return did;
    }

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
        this.did = did;
        this.name = name;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.products = products;
    }
    public static Discount DiscountForSelling(int did, String name, double discountPercent, LocalDateTime startDate, LocalDateTime endDate, ArrayList<Product> products){
        Discount discount = new Discount(did, name, discountPercent, startDate, endDate, products);
        products.forEach((product)-> {
            product.setSellingDiscount(discount);
        });
        discount.type = "Selling";
        return discount;
    }

    public static Discount DiscountForBuying(int did, String name, double discountPercent, LocalDateTime startDate, LocalDateTime endDate, ArrayList<Product> products){
        Discount discount = new Discount(did, name, discountPercent, startDate, endDate, products);
        products.forEach((product)-> {
            product.setBuyingDiscount(discount);
        });
        discount.type = "Buying";
        return discount;
    }

    public static Discount DiscountForBuying(DiscountDTO discountDTO, ArrayList<Product> products){
        return DiscountForBuying(discountDTO.getDid(),discountDTO.getName(),discountDTO.getDiscountPercent(),discountDTO.getStartDate(),discountDTO.getEndDate(),products);
    }

    public static Discount DiscountForSelling(DiscountDTO discountDTO, ArrayList<Product> products){
        return DiscountForSelling(discountDTO.getDid(),discountDTO.getName(),discountDTO.getDiscountPercent(),discountDTO.getStartDate(),discountDTO.getEndDate(),products);
    }

    public void removeProductFromDiscount(Product product){
        products.removeIf((x)->(x==product));
    }

    public boolean discountValid(){
        return (startDate.isBefore(LocalDateTime.now()) && endDate.isAfter(LocalDateTime.now()));
    }



    // TODO implement ACTUAL discount, and add discountPercent field to the diagram
    }
