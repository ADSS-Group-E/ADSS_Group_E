package BusinessLayer;

import PresentationLayer.CategoryDTO;
import PresentationLayer.DiscountDTO;
import PresentationLayer.ProductDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class represents the facade of the project.
 * the purpose of the facade is to hide the complexities of the system
 * and provides an interface to the client using which the client can access the system.
 */
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

    // --------- PRODUCTS ------------
    public void addProduct(ProductDTO newProduct){
        pCont.addProduct(new Product(newProduct, cCont.getCategory(newProduct.getCategoryId())));
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

    public void removeProduct(int pid) {
        pCont.removeProduct(pid);
    }

    public void removeItem(int pid, int id) {
        pCont.getProduct(pid).removeItem(id);
    }

    // --------------- CATEGORIES --------------
    public void addCategory(CategoryDTO newCategory){
        if (newCategory.getSuperCategoryId()==-1){
            cCont.addCategory(new Category(newCategory.getCid(),newCategory.getName()));
        }
        else {
            cCont.addCategory(new Category(newCategory.getCid(), newCategory.getName(),cCont.getCategory(newCategory.getSuperCategoryId())));
        }
    }

    public void addCategory(int cid, String name){
        cCont.addCategory(cid, name);
    }

    public CategoryDTO getCategory(int cid) {
        return new CategoryDTO(cCont.getCategory(cid));
    }

    public ArrayList<CategoryDTO> getCategoryList(){
        ArrayList<CategoryDTO> DTOlist = new ArrayList<CategoryDTO>();
        //Turn products into DTOs
        ArrayList<Category> categoryList = cCont.getList();
        categoryList.forEach((category)-> DTOlist.add(new CategoryDTO(category)));
        return DTOlist;
    }

    public void removeCategory(int cid) {
        cCont.removeCategory(cid);
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

    public String generateInvalidsReport(){
        Report report = rCont.generateInvalidsReport(pCont.getList());
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

    public void removeReport(int rid){
        rCont.removeReport(rid);
    }

    // ------- DISCOUNTS --------

    public DiscountDTO getDiscount(int did){
        return new DiscountDTO(dCont.getDiscount(did));
    }

    public ArrayList<DiscountDTO> getDiscountList(){
        ArrayList<DiscountDTO> DTOlist = new ArrayList<>();
        //Turn products into DTOs
        ArrayList<Discount> discountList = dCont.getList();
        discountList.forEach((discount)-> DTOlist.add(new DiscountDTO(discount)));
        return DTOlist;
    }

    public void addDiscount(int did, String name, double discountPercent, LocalDateTime startDate, LocalDateTime endDate, ArrayList<Integer> cids, ArrayList<Integer> pids ,String type){
        HashSet<Product> productsNoRep =  new HashSet<>();
        cids.forEach((cid)->{
            productsNoRep.addAll(pCont.getAllProductsOfCategory(cCont.getCategory(cid)));
        });
        pids.forEach((pid)->{
            productsNoRep.add(pCont.getProduct(pid));
        });
        ArrayList<Product> products = new ArrayList<>(productsNoRep);

        DiscountDTO discountDTO = new DiscountDTO(did, name, discountPercent, startDate, endDate, pids, type);

        try{
            if (discountDTO.getType().equals("Buying")){
                dCont.addDiscount(Discount.DiscountForBuying(discountDTO,products));
            }
            else
            {
                dCont.addDiscount(Discount.DiscountForSelling(discountDTO,products));
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            System.out.println("Discount not added.");
        }


    }

    public void removeDiscount(int did) {dCont.removeDiscount(did); }
}
