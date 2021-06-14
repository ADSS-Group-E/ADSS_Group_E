package PresentationLayer.Workers.DataTransferObjects;

public class HiringConditionsDTO {
    private double salaryPerHour;
    private String fund;
    private int vacationDays;
    private int sickLeavePerMonth;

    public HiringConditionsDTO(double salaryPerHour, String fund, int vacationDays, int sickLeavePerMonth) {
        this.salaryPerHour = salaryPerHour;
        this.fund = fund;
        this.vacationDays = vacationDays;
        this.sickLeavePerMonth = sickLeavePerMonth;
    }

    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    public String getFund() {
        return fund;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public int getSickLeavePerMonth() {
        return sickLeavePerMonth;
    }

    public void setSalaryPerHour(double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    public void setSickLeavePerMonth(int sickLeavePerMonth) {
        this.sickLeavePerMonth = sickLeavePerMonth;
    }

    public String toString() {
        return "HiringConditions{" +
                "salaryPerHour=" + salaryPerHour +
                ", fund='" + fund + '\'' +
                ", vacationDays=" + vacationDays +
                ", sickLeavePerMonth=" + sickLeavePerMonth +
                '}';
    }
}
