// Provides application entry point.
final class Main {
    // Application entry point.
    public static void main(String[] args) {
        System.out.println("Printer application started.");
        System.out.println("Author: Grosan Flaviu Gheorghe");
        System.out.println();

        // Create a sample print queue.
        SamplePrintQueue<PrintJob> printQueue = new SamplePrintQueue<PrintJob>();

        // Create a sample simulator queue.
        // Do not pass the Event generic type here, since it may handle either a PrintJob or a Printer.
        SampleSimulatorQueue<Event, PrintQueue<PrintJob>> sampleSimulatorQueue = new SampleSimulatorQueue<Event, PrintQueue<PrintJob>>();

        // Create printers, documents, jobs and events, and start queue processing.
        try {
            // Prepare two printers.
            Printer printerOne = new Printer(
                PrintConstants.PAPER_SIZE_SMALL,
                60
            );
            Printer printerTwo = new Printer(
                PrintConstants.PAPER_SIZE_MEDIUM,
                10
            );

            // Set debugging ids.
            printerOne.setId("Test - EPSON");
            printerTwo.setId("Test - HP");

            // Create events for adding printers.
            Event<Printer> addPrinterOneEvent = new Event<Printer>(
                "add-printer",
                printerOne
            );
            Event<Printer> addPrinterTwoEvent = new Event<Printer>(
                "add-printer",
                printerTwo
            );
            // Add an event for removing a printer.
            Event<Printer> removePrinterTwoEvent = new Event<Printer>(
                "remove-printer",
                printerTwo
            );

            // Create a dummy text document for print jobs.
            Document documentOne = new Document("World");
            Document documentTwo = new Document("Hello");
            Document documentThree = new Document("-----");
            // See print job four.
            Document documentFour = new Document("-----");
            // See print job five.
            Document documentFive = new Document("This is a long line that should not be printed.");

            // Create print jobs.
            PrintJob printJobOne = new PrintJob(
                PrintConstants.PAPER_SIZE_SMALL,
                PrintJob.PAPER_COLOUR_YELLOW,
                documentOne
            );
            PrintJob printJobTwo = new PrintJob(
                PrintConstants.PAPER_SIZE_SMALL,
                PrintJob.PAPER_COLOUR_YELLOW,
                documentTwo
            );
            PrintJob printJobThree = new PrintJob(
                // This will not be printed, because an event
                // will remove the printer with this paper size.
                PrintConstants.PAPER_SIZE_MEDIUM,
                PrintJob.PAPER_COLOUR_YELLOW,
                documentThree
            );
            PrintJob printJobFour = new PrintJob(
                PrintConstants.PAPER_SIZE_SMALL,
                PrintJob.PAPER_COLOUR_YELLOW,
                documentFour
            );
            PrintJob printJobFive = new PrintJob(
                // This job will not be printed, because no printer
                // is available for this paper size.
                PrintConstants.PAPER_SIZE_LARGE,
                PrintJob.PAPER_COLOUR_WHITE,
                documentFive
            );

            // Set debugging ids.
            printJobOne.setId("print job ONE");
            printJobTwo.setId("print job TWO");
            printJobThree.setId("print job THREE");
            printJobFour.setId("print job FOUR");
            printJobFive.setId("print job FIVE");

            // Create an event for each print job.
            Event<PrintJob> addPrintJobOne = new Event<PrintJob>(
                "add-print-job",
                printJobOne
            );
            Event<PrintJob> addPrintJobTwo = new Event<PrintJob>(
                "add-print-job",
                printJobTwo
            );
            Event<PrintJob> addPrintJobThree = new Event<PrintJob>(
                "add-print-job",
                printJobThree
            );
            Event<PrintJob> addPrintJobFour = new Event<PrintJob>(
                "add-print-job",
                printJobFour
            );
            Event<PrintJob> addPrintJobFive = new Event<PrintJob>(
                "add-print-job",
                printJobFive
            );

            // Create events for setting a high priority for one event.
            Event<PrintJob> setJobPriorityEvent = new Event<PrintJob>(
                "set-print-job-priority",
                printJobFour,
                PriorityAware.PRIORITY_HIGH
            );

            // Set timestamps (priorities) for each item.
            // NOTE: Since this is not in real time, these timestamps may
            // not be dates in the future.
            addPrinterOneEvent.setPriority(1419187384); // 2014-12-21 - 18:43:04+00:00
            addPrinterTwoEvent.setPriority(1419187412); // 2014-12-21 - 18:43:32+00:00

            addPrintJobTwo.setPriority(1419187413);
            addPrintJobOne.setPriority(1419187414);
            addPrintJobThree.setPriority(1419187416);
            addPrintJobFour.setPriority(1419187417);
            addPrintJobFive.setPriority(1419187418);

            // Change the priority of an item, after printing starts for job two.
            setJobPriorityEvent.setPriority(1419187415);

            // Should be executed after some print jobs are added.
            removePrinterTwoEvent.setPriority(1449187412); // > highest on the list.

            // Begin adding events, in random order, for testing.
            sampleSimulatorQueue.enqueue(addPrinterTwoEvent);
            sampleSimulatorQueue.enqueue(addPrinterOneEvent);
            sampleSimulatorQueue.enqueue(removePrinterTwoEvent);
            sampleSimulatorQueue.enqueue(addPrintJobFour);
            sampleSimulatorQueue.enqueue(addPrintJobThree);
            sampleSimulatorQueue.enqueue(setJobPriorityEvent);
            sampleSimulatorQueue.enqueue(addPrintJobOne);
            sampleSimulatorQueue.enqueue(addPrintJobTwo);
            sampleSimulatorQueue.enqueue(addPrintJobFive);

            // Let the event queue manage itself -> call the start function
            // which will loop items and perform actions on the print queue.
            sampleSimulatorQueue.start(printQueue);
        } catch (PrintJobException e) {
            System.out.println("Exception caught when creating print job: " + e.getMessage());
        } catch (PrinterException e) {
            System.out.println("Exception caught when creating printer: " + e.getMessage());
        } catch (PriorityAwareException e) {
            System.out.println("Exception caught when creating print job: " + e.getMessage());
        } catch (PriorityQueueException e) {
            System.out.println("Exception while handling a queue: " + e.getMessage());
        } catch (EventException e) {
            System.out.println("Exception while creating an event: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Generic exception caught: " + e.getMessage());
        } finally {
            System.out.println();
            System.out.println("Application terminated.");
            System.out.println("Number of events in queue: " + sampleSimulatorQueue.length());
            System.out.println("Number of items not printed: " + printQueue.length());
        }
    }
}
