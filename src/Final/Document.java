// This class is a dummy 'document'. A class that
// holds some text to be printed. Only used for demo purposes.
// This is an immutable object, as once created, it should not be changed.
final class Document {
    // Stores the text to print.
    private final String finalText;

    // Constructor. Provides only means for setting finalText.
    public Document(String text) {
        finalText = text;
    }

    // Gets the text.
    public String getText() {
        return this.finalText;
    }
}