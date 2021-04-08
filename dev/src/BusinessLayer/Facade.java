package BusinessLayer;

import PresentationLayer.DiscountDTO;
import PresentationLayer.ProductDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

public class Facade {
    private ProductController pCont;
    private ReportController rCont;
    private CategoryController cCont;
    private DiscountController dCont;

    public Facade() {
        pCont = new ProductController();
        rCont = new ReportController();
        cCont = new CategoryController();
        dCont = new DiscountController();
    }

    public void addProduct(ProductDTO newProduct){
        pCont.addProduct(new Product(newProduct));
    }

    public ProductDTO getProduct(int pid){
        return new ProductDTO(pCont.getProduct(pid));
    }

    public ArrayList<ProductDTO> getProductList(){
        ArrayList<ProductDTO> DTOlist = new ArrayList<ProductDTO>();
        //Turn products into DTOs
        ArrayList<Product> productList = pCont.getList();
        productList.forEach((product)-> DTOlist.add(new ProductDTO(product)));
        return DTOlist;
    }

    public void addItemToStore(int pid, int id, LocalDateTime expiration){
        pCont.getProduct(pid).addItemToStore(id,expiration);
    }

    public void addItemToStorage(int pid, int id, LocalDateTime expiration){
        pCont.getProduct(pid).addItemToStorage(id,expiration);
    }

    // --------- REPORTS ------------
    public String generateStockReport(ArrayList<Integer> cids, ArrayList<Integer> pids){
        HashSet<Product> products =  new HashSet<>();
        cids.forEach((cid)->{
            products.addAll(pCont.getAllProductsOfCategory(cCont.getCategory(cid)));
        });
        pids.forEach((pid)->{
            products.add(pCont.getProduct(pid));
        });

        Report report = rCont.generateStockReport(new ArrayList<>(products));
        return report.toString();
    }

    public String getReport(int rid){
        return rCont.getReport(rid).toString();
    }
    public ArrayList<String> getReportList(){
        ArrayList<String> stringList = new ArrayList<>();
        //Turn products into DTOs
        ArrayList<Report> reportList = rCont.getList();

        reportList.forEach((report)-> {
            stringList.add(String.format("%-10d%-20s%-20s",report.getRid(),report.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),report.getType()));
        });
        return stringList;
    }

    // ------- DISCOUNTS --------

    public DiscountDTO getDiscount(int did){
        return new DiscountDTO(dCont.getDiscount(did));
    }

    public void addDiscount(DiscountDTO discountDTO){
        ArrayList<Product> products = new ArrayList<>();
        discountDTO.getPids().forEach((pid)->{
            products.add(pCont.getProduct(pid));
        });

        if (discountDTO.getType().equals("Buying")){
            dCont.addDiscount(Discount.DiscountForBuying(discountDTO,products));
        }
        else
        {
            dCont.addDiscount(Discount.DiscountForSelling(discountDTO,products));
        }
    }
}
