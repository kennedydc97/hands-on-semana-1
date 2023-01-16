package main.util;

import main.model.Billing;
import main.model.Installment;
import main.model.Invoice;

import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    private static final Logger LOGGER = Logger.getLogger(FileManager.class.getName());
    private static final String ERROR_MESSAGE = "Invalid value -> %s";
    static final String PATH = "src/resources/";


    public void writeFile(List<String> companies, String fileName) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(String.format("%s/%s.txt", PATH, fileName), true))) {
            for (String company : companies) {
                bufferedWriter.append(company).append("\n");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public List<Invoice> readInvoiceFile(String fileName) {
        List<Invoice> invoices = new ArrayList<>();
        List<String> errorList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(String.format("%s/%s.txt", new File(PATH).getAbsolutePath(), fileName)))) {
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            Instant start = Instant.now();
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
                } catch (Exception e) {
                    errorList.add(invoice.toString());
                } finally {
                    line = bufferedReader.readLine();
                }
                if(Duration.between(start, Instant.now()).getSeconds() > 30) {
                    if(Duration.between(start, Instant.now()).getSeconds() > 300)
                        System.out.println("stop to evaluate");
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

    public List<Billing> readBillingFile(String fileName) {
        List<Billing> billingList = new ArrayList<>();
        List<String> errorList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(String.format("%s/%s.txt", new File(PATH).getAbsolutePath(), fileName)))) {
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            while (line != null) {
                var billing = new Billing();
                try {
                    String[] billingFieldsFromCsv = line.split(";");
                    billing.setCompany(billingFieldsFromCsv[0]);
                    billing.setMonth(readIntegerValue(billingFieldsFromCsv[1]));
                    billing.setYear(readIntegerValue(billingFieldsFromCsv[2]));
                    billing.setFirstInstallment(new Installment(billingFieldsFromCsv[3], billingFieldsFromCsv[4]));
                    billing.setSecondInstallment(new Installment(billingFieldsFromCsv[5], billingFieldsFromCsv[6]));
                    billing.setThirdInstallment(new Installment(billingFieldsFromCsv[7], billingFieldsFromCsv[8]));
                    billing.calculateTotalValue();
                    billingList.add(billing);
                } catch (Exception e) {
                    errorList.add(billing.toString());
                } finally {
                    line = bufferedReader.readLine();
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        if(!errorList.isEmpty()) {
            errorList.forEach(i -> LOGGER.log(Level.SEVERE, String.format("Invoice with missing fields -> %s", i)));
        }
        return billingList;
    }

    private Long readLongValue(String longValue) throws Exception {
        try {
            return Long.parseLong(longValue);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format(ERROR_MESSAGE, longValue));
            throw new Exception(String.format(ERROR_MESSAGE, longValue));
        }
    }

    private Integer readIntegerValue(String intValue) throws Exception {
        try {
            return Integer.parseInt(intValue);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format(ERROR_MESSAGE, intValue));
            throw new Exception(String.format(ERROR_MESSAGE, intValue));
        }
    }

    public static BigDecimal readBigDecimalValue(String bigDecimalValue) throws Exception {
        try {
            return BigDecimal.valueOf(convertToLocalCurrency(bigDecimalValue));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format(ERROR_MESSAGE, bigDecimalValue));
            throw new Exception(String.format(ERROR_MESSAGE, bigDecimalValue));
        }
    }

    public static Double convertToLocalCurrency(String value) {
        Locale locale = new Locale.Builder().setLanguage("pt").setRegion("BR").build();

        try {
            NumberFormat format = NumberFormat.getNumberInstance(locale);
            return format.parse(value).doubleValue();

        } catch (Exception e) {
            LOGGER.severe(String.format("Value doesn't match the type => %s", value));
        }

        return 0.0;
    }


}
