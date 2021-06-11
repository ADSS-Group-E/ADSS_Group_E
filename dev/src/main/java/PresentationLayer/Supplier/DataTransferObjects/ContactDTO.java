package PresentationLayer.Supplier.DataTransferObjects;

import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

public class ContactDTO extends DataTransferObject {
    private final int companyNumber;
    private final String name;
    private final String email;

    public ContactDTO(int id, int companyNumber, String name, String email) {
        super(id);
        this.companyNumber = companyNumber;
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
