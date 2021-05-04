package PresentationLayer.Supplier.DataTransferObjects;

public class ContactDTO {
    private final String name;
    private final String email;

    public ContactDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
