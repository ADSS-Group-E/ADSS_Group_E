package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.DomainObjects.Category;
import BusinessLayer.Inventory.Controllers.CategoryController;
import BusinessLayer.Inventory.Controllers.ProductController;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import BusinessLayer.Inventory.DomainObjects.Product;
import DataAccessLayer.Inventory.DataAccessObjects.ItemGroupDAO;
import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest extends DomainControllerTest {
    //Additional dependencies
    private CategoryController categoryController;
    private ItemGroupDAO itemGroupDAO;

    //Sample data
    private Category sampleCategory;
    private int sampleCategoryId;

    @BeforeEach
    void setUp() {
        categoryController = mock(CategoryController.class);
        DAO = mock(ProductDAO.class);
        itemGroupDAO = mock(ItemGroupDAO.class);

        domainController = new ProductController(categoryController, (ProductDAO) DAO, itemGroupDAO);
        domainControllerSpy = spy(domainController);

        sampleDomainObjectId = 1;
        sampleCategoryId = 1;
        sampleCategory = new Category(sampleCategoryId,"Juice");
        sampleDomainObject = new Product(sampleDomainObjectId, "Test Juice", "AB01","B13", "Test Company",10.5, 10.1, 5,sampleCategory);
        sampleDTO = new ProductDTO((Product) sampleDomainObject);

        when(categoryController.getCategory(sampleCategoryId)).thenReturn(sampleCategory);
    }

    @Override
    DomainObject callGetFunction(int id) {
        return ((ProductController) domainControllerSpy).getProduct(id);
    }

    @Override
    void callAddFunction(DomainObject domainObject) {
        ((ProductController) domainControllerSpy).addProduct((Product) domainObject);
    }

    @Override
    void callRemoveFunction(int id) {
        ((ProductController) domainControllerSpy).removeProduct(id);
    }
}