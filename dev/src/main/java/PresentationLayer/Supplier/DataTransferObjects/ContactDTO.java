package PresentationLayer.Supplier.DataTransferObjects;

import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

public class ContactDTO extends DataTransferObject {
    private final String name;
    private final String email;

    public ContactDTO(String name, String email, int companyNumber) {
        super(companyNumber);
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getCompanyNumber() { return getId(); }
}
