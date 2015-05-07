// Event specific exceptions.
final class EventException extends Exception {
    // Does nothing special. Used for figuring out which kind of
    // exception has been thrown, in parts of the application.
    // Similar to PrintJobException.
    public EventException(String message) {
        super(message);
    }
}