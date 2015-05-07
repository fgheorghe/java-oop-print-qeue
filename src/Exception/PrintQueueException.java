// Print queue specific exceptions.
final class PrintQueueException extends Exception {
    // Does nothing special. Used for figuring out which kind of
    // exception has been thrown, in parts of the application.
    public PrintQueueException(String message) {
        super(message);
    }
}