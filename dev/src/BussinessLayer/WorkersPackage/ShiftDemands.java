package BussinessLayer.WorkersPackage;

import PresentationLayer.ShiftDemandsDTO;

import java.time.LocalDate;

public class ShiftDemands {
    private LocalDate date;
    private ShiftType shiftType;
    private int cashierAmount;
    private int storeKeeperAmount;
    private int arrangerAmount;
    private int guardAmount;
    private int assistantAmount;
    private boolean deliveryRequired;

    public ShiftDemands(LocalDate date, int cashierAmount, int storeKeeperAmount, int arrangerAmount, int guardAmount, int assistantAmount, ShiftType shiftType) {
        this.date = date;
        this.cashierAmount = cashierAmount;
        this.storeKeeperAmount = storeKeeperAmount;
        this.arrangerAmount = arrangerAmount;
        this.guardAmount = guardAmount;
        this.assistantAmount = assistantAmount;
        this.deliveryRequired=false;
        this.shiftType=shiftType;
    }

    public ShiftDemands(LocalDate date, int cashierAmount, int storeKeeperAmount, int arrangerAmount, int guardAmount, int assistantAmount,ShiftType shiftType, boolean deliveryRequired) {
        this.date = date;
        this.cashierAmount = cashierAmount;
        this.storeKeeperAmount = storeKeeperAmount;
        this.arrangerAmount = arrangerAmount;
        this.guardAmount = guardAmount;
        this.assistantAmount = assistantAmount;
        this.deliveryRequired=deliveryRequired;
    }

    public boolean getDeliveryRequired() {
        return deliveryRequired;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDeliveryRequired(boolean deliveryRequired) {
        this.deliveryRequired = deliveryRequired;
    }

    public ShiftDemands(ShiftDemandsDTO shiftDemandsDTO) {
        this.date = shiftDemandsDTO.getDate();
        this.cashierAmount = shiftDemandsDTO.getCashierAmount();
        this.storeKeeperAmount = shiftDemandsDTO.getStoreKeeperAmount();
        this.arrangerAmount = shiftDemandsDTO.getArrangerAmount();
        this.guardAmount = shiftDemandsDTO.getGuardAmount();
        this.assistantAmount = shiftDemandsDTO.getAssistantAmount();
        this.deliveryRequired=shiftDemandsDTO.getDeliveryRequired();
    }
    public ShiftDemands(ShiftDemands shiftDemands) {
        this.date = shiftDemands.getDate();
        this.cashierAmount = shiftDemands.getCashierAmount();
        this.storeKeeperAmount = shiftDemands.getStoreKeeperAmount();
        this.arrangerAmount = shiftDemands.getArrangerAmount();
        this.guardAmount = shiftDemands.getGuardAmount();
        this.assistantAmount = shiftDemands.getAssistantAmount();
        this.deliveryRequired=shiftDemands.getDeliveryRequired();

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
