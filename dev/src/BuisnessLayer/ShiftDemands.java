package BuisnessLayer;

import java.util.Date;

public class ShiftDemands {
    private int cashierAmount;
    private int storeKeeperAmount;
    private int arrangerAmount;
    private int guardAmount;
    private int assistantAmount;

    public ShiftDemands(int cashierAmount, int storeKeeperAmount, int arrangerAmount, int guardAmount, int assistantAmount) {
        this.cashierAmount = cashierAmount;
        this.storeKeeperAmount = storeKeeperAmount;
        this.arrangerAmount = arrangerAmount;
        this.guardAmount = guardAmount;
        this.assistantAmount = assistantAmount;
    }

    public int getCashierAmount() {
        return cashierAmount;
    }

    public void setCashierAmount(int cashierAmount) {
        this.cashierAmount = cashierAmount;
    }

    public int getStoreKeeperAmount() {
        return storeKeeperAmount;
    }

    public void setStoreKeeperAmount(int storeKeeperAmount) {
        this.storeKeeperAmount = storeKeeperAmount;
    }

    public int getArrangerAmount() {
        return arrangerAmount;
    }

    public void setArrangerAmount(int arrangerAmount) {
        this.arrangerAmount = arrangerAmount;
    }

    public int getGuardAmount() {
        return guardAmount;
    }

    public void setGuardAmount(int guardAmount) {
        this.guardAmount = guardAmount;
    }

    public int getAssistantAmount() {
        return assistantAmount;
    }

    public void setAssistantAmount(int assistantAmount) {
        this.assistantAmount = assistantAmount;
    }
}
