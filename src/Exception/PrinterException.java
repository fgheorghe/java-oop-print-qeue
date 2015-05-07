// Printer specific exceptions.
final class PrinterException extends Exception {
    // Does nothing special. Used for figuring out which kind of
    // exception has been thrown, in parts of the application.
    // Similar to PrintJobException.
    public PrinterException(String message) {
        super(message);
    }
}