package main;

import main.start.StartInvoiceProcess;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            LOGGER.info("Application started");

            StartInvoiceProcess startInvoiceProcess = new StartInvoiceProcess();

            startInvoiceProcess.startInvoiceProcess();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format("%s%s", "Error: ", e.getMessage()));
            System.exit(0);
        }
        LOGGER.info("Application finished");

    }
}