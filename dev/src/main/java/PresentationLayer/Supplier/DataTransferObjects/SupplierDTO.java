package PresentationLayer.Supplier.DataTransferObjects;

public class SupplierDTO {
    private final int companyNumber;
    private final String name;
    private final String bankAccount;
    private final String paymentMethod;

    public SupplierDTO(int companyNumber, String name, String bankAccount, String paymentMethod) {
        this.companyNumber = companyNumber;
        this.name = name;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentMethod;
    }

    public int getCompanyNumber() {
        return companyNumber;
    }

    public String getName() {
        return name;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
