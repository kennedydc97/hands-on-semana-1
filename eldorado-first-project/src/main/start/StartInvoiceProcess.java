package main.start;

import main.model.Billing;
import main.model.Invoice;
import main.util.FileManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

//        System.out.println(invoiceMap.get("f9af79a2edb58bc6327ea8bd00e21fb8ca5b55a56077f5664480a23cdd2097e7"));
//        System.out.println(billingMap.get("f9af79a2edb58bc6327ea8bd00e21fb8ca5b55a56077f5664480a23cdd2097e7"));

//        Empresa em conformidades: aquelas cujo valores das notas mensais são
//        iguais aos valores faturados.
//
//        Empresa em não conformidades: aquelas cujo valores das notas mensais não
//        são iguais aos valores faturados.

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
    }

}
