package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.DomainObjects.Category;
import BusinessLayer.Inventory.Controllers.CategoryController;
import BusinessLayer.Inventory.DomainObjects.DomainObject;
import BusinessLayer.Inventory.DomainObjects.Product;
import DataAccessLayer.Inventory.DataAccessObjects.CategoryDAO;
import DataAccessLayer.Inventory.DataAccessObjects.ItemGroupDAO;
import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class CategoryControllerTest extends DomainControllerTest{

    @BeforeEach
    void setUp() {
        DAO = mock(CategoryDAO.class);

        domainController = new CategoryController( (CategoryDAO) DAO);
        domainControllerSpy = spy(domainController);

        sampleDomainObjectId = 1;
        sampleDomainObject = new Category(1,"Juice");
        sampleDTO = new CategoryDTO((Category) sampleDomainObject);
    }

    @Override
    DomainObject callGetFunction(int id) {
        return ((CategoryController) domainControllerSpy).getCategory(id);
    }

    @Override
    void callAddFunction(DomainObject domainObject) {
        ((CategoryController) domainControllerSpy).addCategory((Category) domainObject);
    }

    @Override
    void callRemoveFunction(int id) {
        ((CategoryController) domainControllerSpy).removeCategory(id);
    }
}