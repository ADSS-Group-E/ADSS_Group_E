package PresentationLayer.Supplier.DataTransferObjects;

import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

import java.util.ArrayList;

public class SupplierDTO extends DataTransferObject {
    private final String name;
    private final String bankAccount;
    private final String paymentMethod;
    private int quantityWriterId;

    public SupplierDTO(int companyNumber, String name, String bankAccount, String paymentMethod) {
        super(companyNumber); // TODO ID things
        this.name = name;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentMethod;
        this.quantityWriterId = -1;
    }

    public int getCompanyNumber() {
        return getId();
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

    public int getQuantityWriterId() {
        return quantityWriterId;
    }
}
