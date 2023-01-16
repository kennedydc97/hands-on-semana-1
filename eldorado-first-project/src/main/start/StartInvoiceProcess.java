package main.start;

import main.model.Billing;
import main.model.Invoice;
import main.util.FileManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class StartInvoiceProcess {

    private final String INVOICE = "nota";
    private final String BILLING = "faturamento";

    public void startFilesProcess() throws Exception{
        final FileManager fileManager = new FileManager();
        List<Invoice> invoiceList = fileManager.readInvoiceFile(INVOICE);
        List<Billing> billingList = fileManager.readBillingFile(BILLING);

        Map<String, List<Invoice>> invoiceMap = invoiceList.stream().collect(Collectors.groupingBy(Invoice::getCompany));

        Map<String, List<Billing>> billingMap = billingList.stream().collect(groupingBy(Billing::getCompany));

        List<String> compliantCompanies = new ArrayList<>();
        List<String> nonCompliantCompanies = new ArrayList<>();

        for(String company: billingMap.keySet()) {
            try{
                BigDecimal totalBillingValueOfTheCompany = billingMap.get(company).stream()
                        .map(Billing::getTotalValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                boolean companyHasInvoice = invoiceMap.get(company) != null;
                BigDecimal totalSumInvoiceValuesOfTheCompany = companyHasInvoice ? invoiceMap.get(company).stream()
                        .map(Invoice::getValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add) : BigDecimal.ZERO;
                if(totalBillingValueOfTheCompany.equals(totalSumInvoiceValuesOfTheCompany) && companyHasInvoice)
                    compliantCompanies.add(company);
                else
                    nonCompliantCompanies.add(company);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Size of the list of compliant companies:");
        System.out.println(compliantCompanies.size());
        System.out.println("Size of the list of non compliant companies");
        System.out.println(nonCompliantCompanies.size());
        compliantCompanies.add(0, "Compliant companies");
        nonCompliantCompanies.add(0, "Non compliant companies");
        List<String> outputList = new ArrayList<>(compliantCompanies);
        outputList.addAll(nonCompliantCompanies);
        fileManager.writeFile(outputList, "Report");
    }

}
