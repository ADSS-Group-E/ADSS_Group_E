package BusinessLayer.Supplier.Controllers;

import BusinessLayer.Inventory.Controllers.DomainController;
import BusinessLayer.Inventory.DomainObjects.DomainObject;
import BusinessLayer.Supplier.DomainObjects.Supplier;
import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

public class SupplierProductController extends DomainController {
    Supplier supplier;

    public SupplierProductController(DataAccessObject DAO) {
        super(DAO);
    }

    @Override
    protected DomainObject buildDomainObjectFromDto(DataTransferObject dataTransferObject) {
        return null;
    }

    @Override
    protected DataTransferObject buildDtoFromDomainObject(DomainObject domainObject) {
        return null;
    }
}
