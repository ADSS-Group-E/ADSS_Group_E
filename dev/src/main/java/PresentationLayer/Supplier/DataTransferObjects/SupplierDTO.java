package PresentationLayer.Supplier.DataTransferObjects;

import java.util.ArrayList;

public class SupplierDTO {
    private final int companyNumber;
    private final String name;
    private final String bankAccount;
    private final String paymentMethod;
    private final ArrayList<OrderDTO> orders;

    public SupplierDTO(int companyNumber, String name, String bankAccount, String paymentMethod, ArrayList<OrderDTO> orders) {
        this.companyNumber = companyNumber;
        this.name = name;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentMethod;
        this.orders = orders;
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

    public ArrayList<OrderDTO> getOrders() { return orders; }
}
