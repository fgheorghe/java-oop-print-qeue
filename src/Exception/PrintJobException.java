// Print job specific exceptions.
final class PrintJobException extends Exception {
    // Does nothing special. Used for figuring out which kind of
    // exception has been thrown, in parts of the application.
    public PrintJobException(String message) {
        super(message);
    }
}