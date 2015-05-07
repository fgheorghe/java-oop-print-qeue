// Extends the abstract SimulatorQueue, providing means for actually executing events.
// This sample simulator takes along the generic Event type, a generic print queue type.
final class SampleSimulatorQueue<E extends Event, T extends PrintQueue<PrintJob>> extends SimulatorQueue<E> {
    // This function will loop items and execute events.
    public void start(T printQueue) throws PriorityQueueException, Exception {
        // Print some debug statements.
        System.out.println("Number of events in queue: " + this.length());

        // Apply events against the print queue.
        do {
            // Get the first event in queue.
            E event = this.head();

            // Print the event name, subject id and timestamp - for debugging.
            System.out.println("Event name: "
                + event.getName()
                + ", timestamp: "
                + event.getPriority()
                + ", subject: "
                + event.getSubject().getId());

            // Apply this event to the queue.
            this.applyEvent(event, printQueue);

            // Remove the event from the queue.
            this.dequeue();
        } while(this.length() > 0);

        // Print all items.
        System.out.println();
        while (!printQueue.isEmpty()) {
            PrintJob printJob = (PrintJob) printQueue.head();
            // Emulate a print.
            printQueue.print(printJob);
            // Remove top item from queue.
            printQueue.dequeue();
        }
    }

    // Method used for applying events against the print queue - or subjects.
    private void applyEvent(E event, T printQueue) throws Exception {
        // Handle each type of event.
        switch (event.getName()) {
            case "add-printer":
                printQueue.addPrinter((Printer) event.getSubject());
                break;
            case "add-print-job":
                PrintJob printJob = (PrintJob) event.getSubject();
                printQueue.enqueue(printJob);
                break;
            case "remove-printer":
                printQueue.removePrinter((Printer) event.getSubject());
                break;
            case "set-print-job-priority":
                PrintJob printJobToUpdate = (PrintJob) event.getSubject();
                printJobToUpdate.setPriority(event.getParameter());
                break;
            default:
                throw new Exception("Unknown event type: " + event.getName());
        }
    }
}