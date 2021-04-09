package PresentationLayer;

public class BankAccountDTO {
    private String bankName;
    private String branch;
    private String bankAccount;

    public BankAccountDTO(String bankName, String branch, String bankAccount) {
        this.bankName = bankName;
        this.branch = branch;
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBranch() {
        return branch;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String toString() {
        return "BankAccount{" +
                "bankName='" + bankName + '\'' +
                ", branch='" + branch + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                '}';
    }
}
