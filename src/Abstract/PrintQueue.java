// Provides print queue functionality. See different functions for details about how
// it works.
abstract class PrintQueue<T extends PrintJob> implements PriorityQueueInterface<T> {
    // Stores printers that can handle the queue items.
    private Printer[] printers;

    // Similar to IntegerLists example, found in course materials,
    // this stores queue items in a linked list.
    // TODO: Make this Iterable.
    private class TList {
        private T head;
        private TList tail;

        public void setHead(T head) {
            this.head = head;
        }
        public T getHead() {
            return this.head;
        }
        public void setTail(TList tail) {
            this.tail = tail;
        }
        public TList getTail() {
            return this.tail;
        }
    }

    // Lets child classes do the actual printing.
    abstract public void print(T job);

    // Stores item count, default to 0. This number is maintained by
    // enqueue and dequeue to avoid iterations.
    private int length = 0;

    // Stores the first item added to the queue (first item in the linked list).
    TList firstItem;

    public void enqueue(T item) {
        // Increment the number of queue items.
        this.setLength(this.length() + 1);

        // Check if the first item is null,
        // if so...then create the first item in the TList linked list.
        if (this.getFirstItem() == null) {
            this.setFirstItem(new TList());
            // ...and set the head as the newly added queue item.
            this.getFirstItem().setHead(item);
        } else {
            // Iterate until we reach the end of the items list.
            TList lastItem = firstItem;
            while (lastItem.getTail() != null) {
                lastItem = lastItem.getTail();
            }

            // Set the new tail of the existing list, as a new list made only
            // of the new item.
            TList tail = new TList();
            tail.setHead(item);
            lastItem.setTail(tail);
        }
    }

    public T head() throws PriorityQueueException {
        // First ...check if there is anything in the list, and if not, throw an exception.
        if (this.isEmpty()) {
            throw new PriorityQueueException("Trying to access a top item in an empty print queue.");
        }

        // Returning the highest priority item means iterating the item list,
        // and finding the first highest getPriority() value that a
        // printer exists for. It does not mean returning this.firstItem.

        // Start on the assumption that the first item is the highest on the list.
        T head = this.getFirstItem().getHead();

        TList items = this.getFirstItem();
        do {
            // Replace head with a higher priority item, that has a printer available.
            if (items.getHead().getPriority() > head.getPriority()
                && this.canPrint(items.getHead())) {
                head = items.getHead();
            } else if (items.getHead().getPriority() <= head.getPriority()
                // Or replace it with an item of lower priority, with a printer available.
                && !this.canPrint(head)) {
                head = items.getHead();
            }

            items = items.getTail();
        } while(items != null);

        // Check again if the final head can be printed. If not, throw an exception.
        // This occurs when we reach the end of the list, but no printers to handle remaining
        // items. This exception is actually expected when nothing can process documents left.
        // In a real application the user would add more printers, and the simulation queue would
        // notify this queue, and resume printing.
        if (!this.canPrint(head)) {
            throw new PriorityQueueException("No printer can print the current jobs. Add a new printer or remove jobs.");
        }

        return head;
    }

    public void dequeue() throws PriorityQueueException {
        // Same as for head, check if there is anything to remove.
        if (this.isEmpty()) {
            throw new PriorityQueueException("Trying to remove a top item from an empty list.");
        }

        // Get the top item.
        T job = this.head();

        // If this is the first item in the queue, remove it straight away.
        if (job.equals(this.getFirstItem().getHead())) {
            // Set the head, to the head of tail, if any.
            if (this.getFirstItem().getTail() != null) {
                this.getFirstItem().setHead(this.getFirstItem().getTail().getHead());
                this.getFirstItem().setTail(this.getFirstItem().getTail().getTail());
            }
            // Update length.
            this.setLength(this.length() - 1);
        } else {
            // Otherwise, need to go through the full list.
            TList itemList = this.getFirstItem();
            TList prevList; // Stores the item right before the next item...
            while (itemList.getTail() != null) {
                prevList = itemList;
                itemList = itemList.getTail();

                if (job.equals(itemList.getHead())) {
                    if (itemList.getTail() != null) {
                        itemList.setHead(itemList.getTail().getHead());
                        itemList.setTail(itemList.getTail().getTail());
                    } else {
                        // ...the previous item has been skipped (i.e.: not printer available),
                        // and the item we are removing has no tail. So remove the tail of the
                        // previous item.
                        prevList.setTail(null);
                    }
                    this.setLength(this.length() - 1);
                }
            }
        }
    }

    // The list is empty if length is 0.
    public Boolean isEmpty() {
        return this.length == 0;
    }

    public int length() {
        return this.length;
    }

    // Add a printer to handle this queue.
    public void addPrinter(Printer printer) {
        // Extend the printer array by creating a new array of printer length + 1, as per:
        // http://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html

        // First, figure out how many printers we have.
        int size = this.printers == null ? 0 : this.printers.length;

        // Allocate memory for a new, temporary, variable.
        Printer[] tempPrinters = new Printer[++size];

        // Copy existing printers in the new array.
        int i;
        for (i = 0; i < size - 1; i++) {
            tempPrinters[i] = this.printers[i];
        }
        // Add the new printer.
        tempPrinters[i++] = printer;
        // And replace the printers array.
        this.printers = tempPrinters;
    }

    // Remove a printer handling this queue...if any. Otherwise silently ignore.
    public void removePrinter(Printer printer) {
        // Check if any printers are attached, and if so, remove a printer.
        if (this.printers != null) {
            // Rebuild the printer array, excluding this printer.
            int size = this.printers.length;

            // Allocate memory for a new, temporary, variable with a length of printers length - 1.
            Printer[] tempPrinters = new Printer[size - 1];
            // Track the number of printers copied over.
            int count = 0;
            for (int i = 0; i < size; i++) {
                if (!this.printers[i].equals(printer)) {
                    // Only copy to the new array if it is not the printer we are removing.
                    tempPrinters[count] = this.printers[i];
                    // Increment the number of printers copied over.
                    count++;
                }
            }
            // Replace the printers array.
            this.printers = count == 0 ? null : tempPrinters;
        }
    }

    // Gets printers...if any.
    public Printer[] getPrinters() throws PriorityQueueException {
        if (this.printers == null) {
            throw new PriorityQueueException("No printers configured.");
        }
        return this.printers;
    }

    // Helper for setting the firstItem property.
    private void setFirstItem(TList firstItem) {
        this.firstItem = firstItem;
    }

    // Helper for getting the firstItem property.
    private TList getFirstItem() {
        return this.firstItem;
    }

    // Helper used for checking if we can print a given print job (if a printer is available for the given
    // paper size).
    private Boolean canPrint(T item) throws PriorityQueueException {
        // Get available printers.
        Printer[] printers = this.getPrinters();
        // Iterate printers, and if we have at least one we can use, then return true.
        for (int i = 0; i < printers.length; i++) {
            if (item.getPaperSize() == printers[i].getPaperSize()) {
                return true;
            }
        }
        // Else return false.
        return false;
    }

    // Helper for setting the length of the queue.
    private void setLength(int length) {
        this.length = length;
    }
}