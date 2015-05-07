// Extends the abstract PrintQueue, providing means for actually printing text,
// for debugging purposes, it prints to the command line.
final class SamplePrintQueue<T extends PrintJob> extends PrintQueue<T> {
    // Method used for emulating a print.
    public void print(T job) {
        System.out.println("Printing job id " + job.getId() + ": " + job.getDocument().getText());
    }
}