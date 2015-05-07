// Provides common 'constants', such as paper sizes and common functionality
// for setting these.
abstract class PrintConstants {
    // Common paper sizes.
    public static final int PAPER_SIZE_SMALL = 1;
    public static final int PAPER_SIZE_MEDIUM = 2;
    public static final int PAPER_SIZE_LARGE = 3;

    // Stores configured paper size.
    private int paperSize;

    // Sets the paper size. Must only be called by a constructor,
    // as paper size can not be changed once a job has been
    // created. Throws an exception if invalid paper size is set.
   protected void setPaperSize(int paperSize) throws Exception {
        // Validate input parameters.
        if (paperSize != this.PAPER_SIZE_SMALL
            && paperSize != this.PAPER_SIZE_MEDIUM
            && paperSize != this.PAPER_SIZE_LARGE) {
            throw new Exception("Invalid paper size: "
                + paperSize
                + ". See class PrintConstants for valid values.");
        }
        this.paperSize = paperSize;
   }

   // Gets the paper size. This method can be public, as
   // we need to be able to read job properties.
   public int getPaperSize() {
        return this.paperSize;
   }
}