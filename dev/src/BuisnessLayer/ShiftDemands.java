package BuisnessLayer;

import PresentationLayer.ShiftDemandsDTO;

import java.time.LocalDate;
import java.util.Date;

public class ShiftDemands {
    private LocalDate date;
    private int cashierAmount;
    private int storeKeeperAmount;
    private int arrangerAmount;
    private int guardAmount;
    private int assistantAmount;

    public ShiftDemands(LocalDate date, int cashierAmount, int storeKeeperAmount, int arrangerAmount, int guardAmount, int assistantAmount) {
        this.date = date;
        this.cashierAmount = cashierAmount;
        this.storeKeeperAmount = storeKeeperAmount;
        this.arrangerAmount = arrangerAmount;
        this.guardAmount = guardAmount;
        this.assistantAmount = assistantAmount;
    }
    public ShiftDemands(ShiftDemandsDTO shiftDemandsDTO) {
        this.date = shiftDemandsDTO.getDate();
        this.cashierAmount = shiftDemandsDTO.getCashierAmount();
        this.storeKeeperAmount = shiftDemandsDTO.getStoreKeeperAmount();
        this.arrangerAmount = shiftDemandsDTO.getArrangerAmount();
        this.guardAmount = shiftDemandsDTO.getGuardAmount();
        this.assistantAmount = shiftDemandsDTO.getAssistantAmount();
    }
    public ShiftDemands(ShiftDemands shiftDemandsDTO) {
        this.date = shiftDemandsDTO.getDate();
        this.cashierAmount = shiftDemandsDTO.getCashierAmount();
        this.storeKeeperAmount = shiftDemandsDTO.getStoreKeeperAmount();
        this.arrangerAmount = shiftDemandsDTO.getArrangerAmount();
        this.guardAmount = shiftDemandsDTO.getGuardAmount();
        this.assistantAmount = shiftDemandsDTO.getAssistantAmount();
    }

    public LocalDate getDate() {
        return date;
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

    @Override
    public String toString() {
        return "ShiftDemands{" +
                "cashierAmount=" + cashierAmount +
                ", storeKeeperAmount=" + storeKeeperAmount +
                ", arrangerAmount=" + arrangerAmount +
                ", guardAmount=" + guardAmount +
                ", assistantAmount=" + assistantAmount +
                '}';
    }
}
