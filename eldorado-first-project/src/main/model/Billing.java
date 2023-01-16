package main.model;

import java.math.BigDecimal;

public class Billing {
    //COMPANY;MES;ANO;DATA_PARCELA_1;PARCELA1;DATA_PARCELA_2;PARCELA_2;DATA_PARCELA_3;PARCELA_3
    private String company;
    private int month;
    private int year;
    private Installment firstInstallment;
    private Installment secondInstallment;
    private Installment thirdInstallment;
    private BigDecimal totalValue;

    public Billing(){

    }

    public Billing(String company, int month, int year, Installment firstInstallment,
                   Installment secondInstallment, Installment thirdInstallment, BigDecimal totalValue) {
        this.company = company;
        this.month = month;
        this.year = year;
        this.firstInstallment = firstInstallment;
        this.secondInstallment = secondInstallment;
        this.thirdInstallment = thirdInstallment;
        this.totalValue = totalValue;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Installment getFirstInstallment() {
        return firstInstallment;
    }

    public void setFirstInstallment(Installment firstInstallment) {
        this.firstInstallment = firstInstallment;
    }

    public Installment getSecondInstallment() {
        return secondInstallment;
    }

    public void setSecondInstallment(Installment secondInstallment) {
        this.secondInstallment = secondInstallment;
    }

    public Installment getThirdInstallment() {
        return thirdInstallment;
    }

    public void setThirdInstallment(Installment thirdInstallment) {
        this.thirdInstallment = thirdInstallment;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void calculateTotalValue() {
        this.totalValue = this.firstInstallment.getValue()
                .add(this.secondInstallment.getValue())
                .add(this.thirdInstallment.getValue());
    }

    @Override
    public String toString() {
        return "Billing{" +
                "company='" + company + '\'' +
                ", month=" + month +
                ", year=" + year +
                ", firstInstallment=" + firstInstallment +
                ", secondInstallment=" + secondInstallment +
                ", thirdInstallment=" + thirdInstallment +
                ", totalValue=" + totalValue +
                '}';
    }
}
