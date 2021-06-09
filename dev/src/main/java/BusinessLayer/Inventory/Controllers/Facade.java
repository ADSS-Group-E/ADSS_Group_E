package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.DomainObjects.*;
import BusinessLayer.Inventory.DomainObjects.Reports.Report;
import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;
import PresentationLayer.Inventory.DataTransferObjects.DiscountDTO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class represents the Facade of the project.
 * The purpose of the facade is to act as a go-between the Presentation and Business layers of the system,
 * thereby hiding it's complexities and providing an interface to the client using the system.
 */
public class Facade {
    private final ProductController pCont;
    private final ReportController rCont;
    private final CategoryController cCont;
    private final DiscountController dCont;

    public Facade(ProductController pCont, ReportController rCont, CategoryController cCont, DiscountController dCont) {
        this.pCont = pCont;
        this.rCont = rCont;
        this.cCont = cCont;
        this.dCont = dCont;
    }

    // --------- PRODUCTS ------------
    public void addProduct(ProductDTO newProduct){
        pCont.addProduct(new Product(newProduct, cCont.getCategory(newProduct.getCategoryId())));
    }

    // Getters
    public ProductDTO getProduct(int pid){
        Product product = pCont.getProduct(pid);
        if (product == null)
            return null;
        return new ProductDTO(pCont.getProduct(pid));
    }

    public ArrayList<ProductDTO> getProductList(){
        ArrayList<ProductDTO> DTOlist = new ArrayList<>();
        //Turn products into DTOs
        ArrayList<Product> productList = pCont.getAllProducts();
        if (productList == null){
            return null;
        }
        productList.forEach((product)-> DTOlist.add(new ProductDTO(product)));
        return DTOlist;
    }

    // Adders
    public void addItemToStore(int pid, int quantity, double priceBoughtAt, LocalDateTime expiration){
        ItemGroup itemGroup = new ItemGroup(quantity, priceBoughtAt, expiration);
        pCont.getProduct(pid).addItemGroupToStore(itemGroup);
    }

    public void addItemToStorage(int pid,  int quantity, double priceBoughtAt, LocalDateTime expiration){
        ItemGroup itemGroup = new ItemGroup(quantity, priceBoughtAt, expiration);
        pCont.getProduct(pid).addItemGroupToStorage(itemGroup);
    }

    // Removers
    public void removeProduct(int pid) {
        pCont.removeProduct(pid);
    }

    public void removeItem(int pid, int id) {
        pCont.getProduct(pid).removeItem(id);
    }

    // --------------- CATEGORIES --------------

    // Adders
    public void addCategory(CategoryDTO newCategory){
        if (newCategory.getSuperCategoryId()==-1){
            cCont.addCategory(new Category(newCategory.getCid(),newCategory.getName()));
        }
        else {
            cCont.addCategory(new Category(newCategory.getCid(), newCategory.getName(),cCont.getCategory(newCategory.getSuperCategoryId())));
        }
    }

    public void addCategory(int cid, String name){
        cCont.addCategory(new Category(cid, name));
    }

    // Getters
    public CategoryDTO getCategory(int cid) {
        Category category = cCont.getCategory(cid);
        if (category == null)
            return null;
        return new CategoryDTO(cCont.getCategory(cid));
    }

    public ArrayList<CategoryDTO> getCategoryList(){
        ArrayList<CategoryDTO> DTOlist = new ArrayList<>();
        //Turn products into DTOs
        ArrayList<Category> categoryList = cCont.getAllCategories();
        categoryList.forEach((category)-> DTOlist.add(new CategoryDTO(category)));
        return DTOlist;
    }

    // Remover
    public void removeCategory(int cid) {
        cCont.removeCategory(cid);
    }


    // --------- REPORTS ------------

    // Generators
    public int generateStockReport(ArrayList<Integer> cids, ArrayList<Integer> pids){
        HashSet<Product> products =  new HashSet<>();
        cids.forEach((cid)-> products.addAll(pCont.getAllProductsOfCategory(cCont.getCategory(cid))));
        pids.forEach((pid)-> products.add(pCont.getProduct(pid)));

        Report report = rCont.generateStockReport(new ArrayList<>(products));
        return report.getRid();
    }

    public int generateLowStockReport(){
        Report report = rCont.generateLowStockReport(pCont.getAllProducts());
        return report.getRid();
    }

    public int generateInvalidsReport(){
        Report report = rCont.generateInvalidsReport(pCont.getAllProducts());
        return report.getRid();
    }

    // Getters
    public String getReport(int rid){
        return rCont.getReport(rid).toString();
    }

    public ArrayList<String> getReportList(){
        ArrayList<String> stringList = new ArrayList<>();
        //Turn products into DTOs
        ArrayList<Report> reportList = rCont.getList();

        reportList.forEach((report)-> stringList.add(String.format("%-10d%-20s%-20s",report.getRid(),report.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),report.getType())));
        return stringList;
    }

    // Remover
    public void removeReport(int rid){
        rCont.removeReport(rid);
    }


    // ------- DISCOUNTS --------

    // Getters
    public DiscountDTO getDiscount(int did){
        Discount discount = dCont.getDiscount(did);
        return new DiscountDTO(discount, pCont.getAllProductsWithDiscount(discount));
    }

    public ArrayList<DiscountDTO> getDiscountList(){
        ArrayList<DiscountDTO> DTOlist = new ArrayList<>();
        //Turn products into DTOs
        ArrayList<Discount> discountList = dCont.getAllDiscounts();
        discountList.forEach((discount)-> DTOlist.add(new DiscountDTO(discount,  pCont.getAllProductsWithDiscount(discount))));
        return DTOlist;
    }

    // Adder

    /**
     * This function adds a discount to specific products or specific categories.
     * @param discountDTO
     * @param cids category ID
     */
    public void addDiscount(DiscountDTO discountDTO, ArrayList<Integer> cids, ArrayList<Integer> pids){
        HashSet<Product> productsNoRep =  new HashSet<>();
        cids.forEach((cid)-> productsNoRep.addAll(pCont.getAllProductsOfCategory(cCont.getCategory(cid))));
        pids.forEach((pid)-> productsNoRep.add(pCont.getProduct(pid)));
        ArrayList<Product> products = new ArrayList<>(productsNoRep);

        Discount discount = new Discount(discountDTO);

        try{
            dCont.addDiscount(discount , products);
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            System.out.println("Discount not added.");
        }


    }

    // Remover
    public void removeDiscount(int did) {dCont.removeDiscount(did); }
}
