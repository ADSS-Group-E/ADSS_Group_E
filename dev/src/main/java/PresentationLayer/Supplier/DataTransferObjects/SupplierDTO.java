package PresentationLayer.Supplier.DataTransferObjects;

import BusinessLayer.Supplier.DomainObjects.QuantityWriter;
import BusinessLayer.Supplier.DomainObjects.Supplier;
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

    public SupplierDTO(Supplier other) {
        super(other.getId());
        this.name = other.getName();
        this.bankAccount = other.getBankAccount();
        this.paymentMethod=other.getPaymentMethod();

        QuantityWriter quantityWriter = other.getQuantityWriter();
        if (quantityWriter == null){
            this.quantityWriterId = -1;
        }
        else{
            this.quantityWriterId = quantityWriter.getId();
        }
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
