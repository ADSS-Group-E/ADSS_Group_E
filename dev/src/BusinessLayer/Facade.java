package BusinessLayer;

import PresentationLayer.ProductDTO;

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
}
