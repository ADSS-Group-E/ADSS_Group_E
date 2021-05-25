package BussinessLayer.WorkersPackage;

import java.util.Objects;

public class HiringConditions {
    private double salaryPerHour;
    private String fund;
    private int vacationDays;
    private int sickLeavePerMonth;

    public HiringConditions(double salaryPerHour, String fund, int vacationDays, int sickLeavePerMonth) {
        this.salaryPerHour = salaryPerHour;
        this.fund = fund;
        this.vacationDays = vacationDays;
        this.sickLeavePerMonth = sickLeavePerMonth;
    }

    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HiringConditions)) return false;
        HiringConditions that = (HiringConditions) o;
        return Double.compare(that.getSalaryPerHour(), getSalaryPerHour()) == 0 && getVacationDays() == that.getVacationDays() && getSickLeavePerMonth() == that.getSickLeavePerMonth() && Objects.equals(getFund(), that.getFund());
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    public int getSickLeavePerMonth() {
        return sickLeavePerMonth;
    }

    public void setSickLeavePerMonth(int sickLeavePerMonth) {
        this.sickLeavePerMonth = sickLeavePerMonth;
    }

    @Override
    public String toString() {
        return "HiringConditions{" +
                "salaryPerHour=" + salaryPerHour +
                ", fund='" + fund + '\'' +
                ", vacationDays=" + vacationDays +
                ", sickLeavePerMonth=" + sickLeavePerMonth +
                '}';
    }
}
