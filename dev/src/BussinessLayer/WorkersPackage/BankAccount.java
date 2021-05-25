package BussinessLayer.WorkersPackage;

import java.util.Objects;

public class BankAccount {
    private String bankName;
    private String branch;
    private String bankAccount;

    public BankAccount(String bankName, String branch, String bankAccount) {
        this.bankName = bankName;
        this.branch = branch;
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(getBankName(), that.getBankName()) && Objects.equals(getBranch(), that.getBranch()) && Objects.equals(getBankAccount(), that.getBankAccount());
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "bankName='" + bankName + '\'' +
                ", branch='" + branch + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                '}';
    }
}
