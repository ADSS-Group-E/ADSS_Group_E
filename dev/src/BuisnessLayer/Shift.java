package BuisnessLayer;

import java.util.Date;

public class Shift {
    private Date date;
    private ShiftType type;
    private ShiftDemands demands;

    public Shift(Date date, ShiftType type, ShiftDemands demands) {
        this.date = date;
        this.type = type;
        this.demands = demands;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ShiftType getType() {
        return type;
    }

    public void setType(ShiftType type) {
        this.type = type;
    }

    public ShiftDemands getDemands() {
        return demands;
    }

    public void setDemands(ShiftDemands demands) {
        this.demands = demands;
    }
}
