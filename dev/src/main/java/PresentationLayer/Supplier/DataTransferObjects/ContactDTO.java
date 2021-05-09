package PresentationLayer.Supplier.DataTransferObjects;

public class ContactDTO {
    private final String name;
    private final String email;
    private final int companyNumber;

    public ContactDTO(String name, String email, int companyNumber) {
        this.name = name;
        this.email = email;
        this.companyNumber = companyNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getCompanyNumber() { return companyNumber; }
}
