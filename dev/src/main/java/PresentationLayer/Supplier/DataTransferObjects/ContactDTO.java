package PresentationLayer.Supplier.DataTransferObjects;

import BusinessLayer.Supplier.DomainObjects.Contact;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

public class ContactDTO extends DataTransferObject {
    private int companyNumber;
    private final String name;
    private final String email;

    public ContactDTO(int id, int companyNumber, String name, String email) {
        super(id);
        this.companyNumber = companyNumber;
        this.name = name;
        this.email = email;
    }

    public ContactDTO(Contact other) {
        super(other.getId());
        this.name = other.getName();
        this.email = other.getEmail();
    }

    public void setCompanyNumber(int companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getCompanyNumber() { return getId(); }
}
