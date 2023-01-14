package main.start;

import main.util.FileManager;

public class StartInvoiceProcess {

    private final String INVOICE = "nota";

    public void startInvoiceProcess() throws Exception{
        final FileManager fileManager = new FileManager();
        fileManager.readFile(INVOICE);

    }

}
