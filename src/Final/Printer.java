// Provides printer functionality.
final class Printer extends PrintConstants implements IdProvider {
    // Stores the configured printer speed, in pages per minute.
    private int printerSpeed;

    // A printer identifier.
    private String id;

    // Class constructor. Provides the only means for setting
    // the printer speed and paper size.
    public Printer(int paperSize, int printerSpeed) throws PrinterException {
        // Configure printer.
        this.setPrinterSpeed(printerSpeed);
        try {
            this.setPaperSize(paperSize);
        } catch (Exception e) {
            // TODO: Properly copy 'e' details into PrinterException.
            throw new PrinterException(e.getMessage());
        }
    }

    // Sets the printer speed, in pages per minute.
    private void setPrinterSpeed(int printerSpeed) throws PrinterException {
        // Validate input parameters.
        if (printerSpeed <= 0) {
            throw new PrinterException("Invalid printer speed settings. Value must be > 0");
        }
        this.printerSpeed = printerSpeed;
    }

    // Gets the set printer speed.
    public int getPrinterSpeed() {
        return this.printerSpeed;
    }

    // Sets the printer identifier. Mainly used for debugging.
    public void setId(String id) {
        this.id = id;
    }

    // Gets the printer identifier.
    public String getId() {
        return this.id;
    }
}