package dev.src.BusinessLayer;

import java.time.LocalDateTime;

public class Discount {
    private int did;
    private String name;
    private double discountPercent;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Discount(int did, String name, double discountPercent, LocalDateTime startDate, LocalDateTime endDate) {
        this.did = did;
        this.name = name;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getDid() {
        return did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    // TODO implement ACTUAL discount, and add discountPercent field to the diagram
    }
