// Provides event queue functionality. Unlike the PrintQueue, items here are sorted (ascending)
// when added. Furthermore this queue deals with Event type of objects, which inherits
// from PriorityAware interface.
abstract class SimulatorQueue<T extends Event> implements PriorityQueueInterface<T> {
    // Stores queue items (events) in a sorted linked list.
    private class TList implements Cloneable {
        private T head;
        private TList tail;

        public void setHead(T head) {
            // If this position in the linked list does not have
            // a queue item, add one straight away.
            if (this.head == null) {
                this.head = head;
            } else {
                // If the new queue item priority is higher or equal, then push down the
                // linked list chain and leave the current head unchanged.
                if (head.getPriority() > this.getHead().getPriority()) {
                    // If no tail exists, create one.
                    if (this.getTail() == null) {
                        TList tail = new TList();
                        tail.setHead(head);
                        this.setTail(tail);
                    } else {
                        // Otherwise let the tail decide where to put the new head.
                        this.getTail().setHead(head);
                    }
                } else {
                    // If the new queue item has a lower priority, then push the current
                    // head down the chain and replace it - similar to above, but in reverse.
                    if (this.getTail() == null) {
                        // Create a tail if one does not exist.
                        TList tail = new TList();
                        tail.setHead(this.getHead());
                        this.setTail(tail);
                        this.head = head;
                    } else {
                        // If a tail exists, then just swap heads.
                        this.getTail().setHead(this.getHead());
                        this.head = head;
                    }
                }
            }
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

    // Stores the first item added to the queue (first item in the linked list),
    // similar to print queue.
    TList firstItem = new TList();

    // Store the queue length here, to avoid having to iterate items. enqueue and head maintain this value.
    // Similar to PrintQueue.
    private int length;

    // Adds an event to the queue.
    public void enqueue(T item) {
        // Increment the number of queue items.
        this.setLength(this.length() + 1);

        // Add to queue - which in turn will sort items.
        this.getFirstItem().setHead(item);
    }

    public T head() throws PriorityQueueException {
        // First, check if items are set.
        if (this.length() == 0) {
            throw new PriorityQueueException("Event queue is empty. Can not access head.");
        }

        // Highest item on the list is the head of this.firstItem
        // as the linked list is already sorted.
        return this.getFirstItem().getHead();
    }

    public void dequeue() throws PriorityQueueException {
        // First, check if items are set, similar to above.
        if (this.length() == 0) {
            throw new PriorityQueueException("Event queue is empty. Can not dequeue item.");
        }
        // Decrement the number of items.
        this.setLength(this.length() - 1);

        // And replace the firstItem with its the tail.
        this.setFirstItem(this.getFirstItem().getTail());
    }

    public Boolean isEmpty() {
        return this.length() == 0;
    }

    // Sets the length of the queue.
    private void setLength(int length) {
        this.length = length;
    }

    public int length() {
        return this.length;
    }

    // Helper for setting the first item.
    private void setFirstItem(TList firstItem) {
        this.firstItem = firstItem;
    }

    // Helper for getting the first item.
    private TList getFirstItem() {
        return this.firstItem;
    }
}