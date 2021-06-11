package BusinessLayer.Supplier.DomainObjects;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import PresentationLayer.Supplier.DataTransferObjects.ContactDTO;

public class Contact extends DomainObject {
    private final String name;
    private final String email;

    public Contact(int companyNumber, String name, String email) {
        super(companyNumber);
        this.name = name;
        this.email = email;
    }

    public Contact (ContactDTO other){
        super(other.getId());
        this.name = other.getName();
        this.email = other.getEmail();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getCompanyNumber() { return getId(); }
}

