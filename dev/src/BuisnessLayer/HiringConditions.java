package BuisnessLayer;

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

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    public int getSickLeavePerMonth() {
        return sickLeavePerMonth;
    }

    public void setSickLeavePerMonth(int sickLeavePerMonth) {
        this.sickLeavePerMonth = sickLeavePerMonth;
    }
}
