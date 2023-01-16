package main.model;


import main.util.FileManager;

import java.math.BigDecimal;

public class Installment {
    private String date;
    private BigDecimal value;

    public Installment(String date, String value) throws Exception {
        this.date = date;
        this.value = FileManager.readBigDecimalValue(value);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(String value) throws Exception {
        this.value = FileManager.readBigDecimalValue(value);
    }

    @Override
    public String toString() {
        return "Installment{" +
                "date='" + date + '\'' +
                ", value=" + value +
                '}';
    }
}