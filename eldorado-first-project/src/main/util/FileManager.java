package main.util;

import main.model.Invoice;

import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    private static final Logger LOGGER = Logger.getLogger(FileManager.class.getName());

    static final String PATH = "src/resources/";

    public void writeFile(List<Invoice> invoices, String fileName) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(String.format("%s/%s.txt", PATH, fileName), true))) {
            for (Invoice invoice : invoices) {
                bufferedWriter.append(invoice.toString()).append("\n");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public List<Invoice> readFile(String fileName) {
        List<Invoice> invoices = new ArrayList<>();
        List<String> errorList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(String.format("%s/%s.txt", new File(PATH).getAbsolutePath(), fileName)))) {
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            while (line != null) {
                var invoice = new Invoice();
                try {
                    String[] invoiceFieldsFromCsv = line.split(";");
                    invoice.setCompany(invoiceFieldsFromCsv[0]);
                    invoice.setMonth(readIntegerValue(invoiceFieldsFromCsv[1]));
                    invoice.setYear(readIntegerValue(invoiceFieldsFromCsv[2]));
                    invoice.setValue(readBigDecimalValue(invoiceFieldsFromCsv[3]));
                    invoice.setIssueDate(invoiceFieldsFromCsv[4]);
                    invoice.setId(readLongValue(invoiceFieldsFromCsv[5]));
                    invoices.add(invoice);
                    line = bufferedReader.readLine();
                } catch (Exception e) {
                    errorList.add(invoice.toString());
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        if(errorList.size() > 0) {
            errorList.stream().forEach(i -> LOGGER.log(Level.SEVERE, String.format("Invoice with missing fields -> %s", i)));
        }
        return invoices;
    }

    private Long readLongValue(String longValue) throws Exception {
        try {
            return Long.parseLong(longValue);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format("Invalid value -> %s", longValue));
            throw new Exception("Invalid value -> " + longValue);
        }
    }

    private Integer readIntegerValue(String intValue) throws Exception {
        try {
            return Integer.parseInt(intValue);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format("Invalid value -> %s", intValue));
            throw new Exception("Invalid value -> " + intValue);
        }
    }

    private BigDecimal readBigDecimalValue(String bigDecimalValue) throws Exception {
        try {
            return BigDecimal.valueOf(convertToLocalCurrency(bigDecimalValue));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format("Invalid value -> %s", bigDecimalValue));
            throw new Exception("Invalid value -> " + bigDecimalValue);
        }
    }

    public static Double convertToLocalCurrency(String value) {
        Locale locale = new Locale.Builder().setLanguage("pt").setRegion("BR").build();

        try {
            NumberFormat format = NumberFormat.getNumberInstance(locale);
            return format.parse(value).doubleValue();

        } catch (Exception e) {
            LOGGER.severe(String.format("Valor não corresponde com o tipo => %s", value));
        }

        return 0.0;
    }


}
