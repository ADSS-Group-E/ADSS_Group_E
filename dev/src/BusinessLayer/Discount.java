package dev.src.BusinessLayer;

import java.time.format.DateTimeFormatter;

public class Discount {
    private int did;
    private String name;
    private double discountPercent;
    private DateTimeFormatter startDate;
    private DateTimeFormatter endDate;

    public Discount(int did, String name, double discountPercent, DateTimeFormatter startDate, DateTimeFormatter endDate) {
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

    public DateTimeFormatter getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTimeFormatter startDate) {
        this.startDate = startDate;
    }

    public DateTimeFormatter getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTimeFormatter endDate) {
        this.endDate = endDate;
    }

    // TODO implement ACTUAL discount, and add discountPercent field to the diagram
    }
