package BusinessLayer;

import PresentationLayer.ProductDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Facade {
    private ProductController pCont;
    private ReportController rCont;
    private CategoryController cCont;

    public Facade() {
        pCont = new ProductController();
        rCont = new ReportController();
        cCont = new CategoryController();
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
}
