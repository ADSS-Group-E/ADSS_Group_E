package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.Controllers.CategoryController;
import BusinessLayer.Inventory.Controllers.DiscountController;
import BusinessLayer.Inventory.Controllers.ProductController;
import BusinessLayer.Inventory.DomainObjects.Category;
import BusinessLayer.Inventory.DomainObjects.Discount;
import BusinessLayer.Inventory.DomainObjects.DomainObject;
import BusinessLayer.Inventory.DomainObjects.Product;
import DataAccessLayer.Inventory.DataAccessObjects.DiscountDAO;
import DataAccessLayer.Inventory.DataAccessObjects.ItemGroupDAO;
import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.DiscountDTO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiscountControllerTest extends DomainControllerTest{

    //Additional dependencies
    private ProductController productController;

    //Sample data
    private ArrayList<Product> sampleProducts;
    private Category sampleCategory;
    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        productController = mock(ProductController.class);
        DAO = mock(DiscountDAO.class);

        domainController = new DiscountController(productController, (DiscountDAO) DAO);
        domainControllerSpy = spy(domainController);

        sampleDomainObjectId = 1;
        sampleCategory = new Category(1,"Juice");
        sampleProduct = new Product(sampleDomainObjectId, "Test Juice", "AB01","B13", "Test Company",10.5, 10.1, 5,sampleCategory);
        sampleProducts = new ArrayList<>();
        sampleProducts.add(sampleProduct);
        sampleDomainObject = new Discount(1,"Test Spring Discount", 0.1,
                LocalDateTime.of(2021,4,1,16,0),
                LocalDateTime.of(2022,5,1,16,0)
            );
        sampleDTO = new DiscountDTO((Discount) sampleDomainObject, sampleProducts);

        when(productController.getAllProductsWithDiscount((Discount) sampleDomainObject)).thenReturn(sampleProducts);
    }

    @Override
    DomainObject callGetFunction(int id) {
        return ((DiscountController) domainControllerSpy).getDiscount(id);
    }

    @Override
    void callAddFunction(DomainObject domainObject) {
        ((DiscountController) domainControllerSpy).addDiscount((Discount) domainObject, sampleProducts);
    }

    @Override
    void callRemoveFunction(int id) {
        ((DiscountController) domainControllerSpy).removeDiscount(id);
    }
}