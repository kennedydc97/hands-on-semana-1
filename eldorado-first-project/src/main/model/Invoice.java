package main.model;

import java.math.BigDecimal;

public class Invoice {

//    COMPANY;MES;ANO;VALOR;DATA_EMISSAO;NOTA
    private long id;
    private String company;
    private int month;
    private int year;
    private BigDecimal value;

    private String issueDate;


    public Invoice(){

    }

    public Invoice(long id, String company, int month, int year, BigDecimal value, String issueDate) {
        this.id = id;
        this.company = company;
        this.month = month;
        this.year = year;
        this.value = value;
        this.issueDate = issueDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
}
