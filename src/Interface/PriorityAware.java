// Provides 'priority aware' blueprint - each class
// implementing this interface will have a priority set.
interface PriorityAware {
    // Convenience constants, for print queues.
    // Event queues will have their UNIX timestamp as priority.
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_HIGH = 1;

    public void setPriority(int priority) throws PriorityAwareException;
    public int getPriority();
}