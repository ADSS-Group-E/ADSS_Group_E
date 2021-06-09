package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.DomainObjects.Category;
import BusinessLayer.Inventory.DomainObjects.DomainObject;
import BusinessLayer.Inventory.DomainObjects.Product;
import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import DataAccessLayer.Inventory.DataAccessObjects.ItemGroupDAO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

abstract class DomainControllerTest {
    //Class to be tested
    protected DomainController domainController;
    //Spy
    protected DomainController domainControllerSpy;

    //Dependencies - will be mocked
    protected DataAccessObject DAO;

    //Sample data
    protected DomainObject sampleDomainObject;
    protected DataTransferObject sampleDTO;
    protected int sampleDomainObjectId;


    @Test
    void getWhenExists() {
        doReturn(sampleDTO).when(DAO).get(sampleDomainObjectId);
        doReturn(sampleDomainObject).when(domainControllerSpy).buildDomainObjectFromDto(sampleDTO);

        DomainObject result = callGetFunction(sampleDomainObjectId);
        assertEquals(sampleDomainObject, result);
    }

    @Test
    void getWhenNotExists() {
        when(DAO.get(sampleDomainObjectId)).thenReturn(null);

        DomainObject result = callGetFunction(sampleDomainObjectId);
        assertNull(result);
    }

    abstract DomainObject callGetFunction(int id);

    @Test
    void addWhenNotAlreadyExists() {
        when(domainControllerSpy.buildDtoFromDomainObject(sampleDomainObject)).thenReturn(sampleDTO);
        when(DAO.insert(sampleDTO)).thenReturn(true);

        callAddFunction(sampleDomainObject);
        assertEquals(sampleDomainObject,domainControllerSpy.identityMap.get(sampleDomainObjectId));
    }

    @Test
    void addWhenAlreadyExists() {
        when(domainControllerSpy.buildDtoFromDomainObject(sampleDomainObject)).thenReturn(sampleDTO);
        when(DAO.insert(sampleDTO)).thenReturn(false);

        callAddFunction(sampleDomainObject);
        assertNotEquals(sampleDomainObject, domainControllerSpy.identityMap.get(sampleDomainObjectId));
    }

    abstract void callAddFunction(DomainObject domainObject);

    @Test
    void removeWhenExists() {
        domainControllerSpy.identityMap.put(sampleDomainObjectId, sampleDomainObject);
        when(DAO.delete(sampleDomainObjectId)).thenReturn(true);

        callRemoveFunction(sampleDomainObjectId);
        assertNull(domainControllerSpy.identityMap.get(sampleDomainObjectId));
    }

    @Test
    void removeWhenNotExists() {
        when(DAO.delete(sampleDomainObjectId)).thenReturn(false);

        callRemoveFunction(sampleDomainObjectId);
        assertNull(domainControllerSpy.identityMap.get(sampleDomainObjectId));
    }

    abstract void callRemoveFunction(int id);

}
