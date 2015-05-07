// Defines the 'skeleton' of a priority queue.
interface PriorityQueueInterface<T extends PriorityAware> {
    // Adds an item.
    public void enqueue(T item);

    // Fetches the top item. Throws an exception if the queue is empty.
    public T head() throws PriorityQueueException;

    // Removes the top item. Throws an exception if the queue is empty.
    public void dequeue() throws PriorityQueueException;

    // Checks if the queue is empty.
    public Boolean isEmpty();

    // Returns the queue length.
    public int length();
}