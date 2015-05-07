// Priority specific exceptions.
final class PriorityAwareException extends Exception {
    // Does nothing special. Used for figuring out which kind of
    // exception has been thrown, in parts of the application.
    // Similar to PrintJobException.
    public PriorityAwareException(String message) {
        super(message);
    }
}