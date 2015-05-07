// This class provides logic for a print job.
class PrintJob extends PrintConstants implements PriorityAware, IdProvider {
    // Colour 'constants'.
    public static final int PAPER_COLOUR_WHITE = 1;
    public static final int PAPER_COLOUR_YELLOW = 2;
    public static final int PAPER_COLOUR_RED = 3;

    // Stores configuration for the current print job.
    private int paperColour;

    // Stores a print document.
    private Document document;

    // Stores a print job priority.
    private int priority = PriorityAware.PRIORITY_NORMAL; // Default priority is normal.

    // Stores a print job identifier.
    private String id;

    // Class constructor. Provides the only means for
    // setting the print job paper size, colour and document to print.
    public PrintJob(int paperSize, int paperColour, Document document) throws PrintJobException {
        // Store 'configuration'.
        try {
            this.setPaperSize(paperSize);
        } catch (Exception e) {
            // TODO: Properly copy 'e' details into PrintJobException.
            throw new PrintJobException(e.getMessage());
        }
        this.setPaperColour(paperColour);
        this.setDocument(document);
    }

   // Sets the paper colour. Similar to paper size, only called
   // by the class constructor, and throws a PrintJobException if
   // and invalid value is set.
   private void setPaperColour(int paperColour) throws PrintJobException {
        // Validate input parameters.
        if (paperColour != this.PAPER_COLOUR_WHITE
            && paperColour != this.PAPER_COLOUR_YELLOW
            && paperColour != this.PAPER_COLOUR_RED) {
            throw new PrintJobException("Invalid paper colour: "
                + paperColour
                + ". See class PrintJob for valid values.");
        }
        this.paperColour = paperColour;
   }

   // Gets the paper colour. This method can be public, as
   // we need to be able to read job properties.
   public int getPaperColour() {
        return this.paperColour;
   }

   // Similar to paper size and colour, a document can not be changed once added
   // to a print job.
   private void setDocument(Document document) {
        this.document = document;
   }

   // Gets the set document.
   public Document getDocument() {
        return this.document;
   }

   // Implement interface methods.
   public int getPriority() {
        return this.priority;
   }

   // Note: changing a priority of a job, while it's in the queue
   // needs to be reflected when the queue picks up the next item.
   public void setPriority(int priority) throws PriorityAwareException {
        if (priority != this.PRIORITY_NORMAL
            && priority != this.PRIORITY_HIGH) {
            throw new PriorityAwareException("Invalid priority set"
                + priority
                + ". See interface PriorityAware for valid values.");
        }

        this.priority = priority;
   }

    // Sets the print job identifier. Mainly used for debugging.
    public void setId(String id) {
        this.id = id;
    }

    // Gets the printer identifier.
    public String getId() {
        return this.id;
    }
}