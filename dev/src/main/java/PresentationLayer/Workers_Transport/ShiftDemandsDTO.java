package PresentationLayer.Workers_Transport;

import BusinessLayer.Workers_Transport.WorkersPackage.ShiftType;

import java.time.LocalDate;

public class ShiftDemandsDTO {
    private LocalDate date;
    private ShiftType shiftType;
    private int cashierAmount;
    private int storeKeeperAmount;
    private int arrangerAmount;
    private int guardAmount;
    private int assistantAmount;
    private boolean deliveryRequired;


    @Override
    public String toString() {
        return "ShiftDemandsDTO{" +
                "date=" + date +
                ", type=" + shiftType +
                ", cashierAmount=" + cashierAmount +
                ", storeKeeperAmount=" + storeKeeperAmount +
                ", arrangerAmount=" + arrangerAmount +
                ", guardAmount=" + guardAmount +
                ", assistantAmount=" + assistantAmount +
                ", deliveryRequired=" + deliveryRequired +
                '}';
    }

    public ShiftDemandsDTO(LocalDate date, int cashierAmount, int storeKeeperAmount, int arrangerAmount, int guardAmount, int assistantAmount) {
        this.date = date;
        this.cashierAmount = cashierAmount;
        this.storeKeeperAmount = storeKeeperAmount;
        this.arrangerAmount = arrangerAmount;
        this.guardAmount = guardAmount;
        this.assistantAmount = assistantAmount;
        this.deliveryRequired=false;
    }

    public ShiftDemandsDTO(LocalDate date, int cashierAmount, int storeKeeperAmount, int arrangerAmount, int guardAmount, int assistantAmount, boolean deliveryRequired, ShiftType shiftType) {
        this.date = date;
        this.cashierAmount = cashierAmount;
        this.storeKeeperAmount = storeKeeperAmount;
        this.arrangerAmount = arrangerAmount;
        this.guardAmount = guardAmount;
        this.assistantAmount = assistantAmount;
        this.deliveryRequired=deliveryRequired;
        this.shiftType=shiftType;
    }
    public ShiftDemandsDTO(BusinessLayer.Workers_Transport.WorkersPackage.ShiftDemands blShiftDemands) {
        this.date = blShiftDemands.getDate();
        this.cashierAmount = blShiftDemands.getCashierAmount();
        this.storeKeeperAmount = blShiftDemands.getStoreKeeperAmount();
        this.arrangerAmount = blShiftDemands.getArrangerAmount();
        this.guardAmount = blShiftDemands.getGuardAmount();
        this.assistantAmount = blShiftDemands.getAssistantAmount();
    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public void setShiftType(ShiftType shiftType) {
        this.shiftType = shiftType;
    }

    public boolean isDeliveryRequired() {
        return deliveryRequired;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCashierAmount() {
        return cashierAmount;
    }

    public int getStoreKeeperAmount() {
        return storeKeeperAmount;
    }

    public int getArrangerAmount() {
        return arrangerAmount;
    }

    public int getGuardAmount() {
        return guardAmount;
    }

    public int getAssistantAmount() {
        return assistantAmount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCashierAmount(int cashierAmount) {
        this.cashierAmount = cashierAmount;
    }

    public void setStoreKeeperAmount(int storeKeeperAmount) {
        this.storeKeeperAmount = storeKeeperAmount;
    }

    public void setArrangerAmount(int arrangerAmount) {
        this.arrangerAmount = arrangerAmount;
    }

    public void setGuardAmount(int guardAmount) {
        this.guardAmount = guardAmount;
    }

    public void setAssistantAmount(int assistantAmount) {
        this.assistantAmount = assistantAmount;
    }

    public boolean getDeliveryRequired() {
        return deliveryRequired;
    }

    public void setDeliveryRequired(boolean deliveryRequired) {
        this.deliveryRequired = deliveryRequired;
    }
}
