// Provides event data. Each event must be priority aware - in this case,
// the priority is the UNIX timestamp of when an event would occur. Considering
// that dates in future have a higher value their priority will be higher
// thus the simulator queue can order them. Made this as a generic
// class so that it can handle different types of subjects and parameters
// safely. Each subject for an event must be an IdProvider.
class Event<T extends IdProvider> implements PriorityAware {
    // Stores priority - this _should_ be a UNIX timestamp.
    private int priority;

    // Stores an event name, used for figuring out the type of action to perform.
    private String name;

    // Stores the event subject, if any. I.e.: a printer or a print job.
    // Different methods below will store different kinds of objects.
    private T subject;

    // Stores an optional parameter for this event, to be applied against
    // the subject. This can only be an integer.
    private Integer parameter;

    // A constructor for a subject, without parameters.
    public Event(String name, T subject) throws EventException {
        this.setName(name);
        this.setSubject(subject);
    }

    // A constructor for a subject with parameters.
    public Event(String name, T subject, Integer parameter) throws EventException {
        this.setName(name);
        this.setSubject(subject);
        this.setParameter(parameter);
    }

    // Helper for setting the name of this event. This should be private
    // as only the constructor should be used for setting an event name,
    // to ensure an event has the right type of subject, and parameters.
    private void setName(String name) throws EventException{
        // Validate input parameters.
        if (name.toLowerCase() != "add-printer"
            && name.toLowerCase() != "remove-printer"
            && name.toLowerCase() != "add-print-job"
            && name.toLowerCase() != "set-print-job-priority") {
            throw new EventException("Unknown action type: " + name);
        }

        this.name = name.toLowerCase(); // Stored in lower case.
    }

    // Helper function for fetching the name of this event. Set to public.
    public String getName() {
        return this.name;
    }

    // Helper for setting the subject. Similar to setName, only constructors
    // should set the subject to make an event type have the right
    // subject attached.
    private void setSubject(T subject) {
        this.subject = subject;
    }

    // Helper for fetching the subject.
    public T getSubject() {
        return this.subject;
    }

    // Helper for setting an event parameter. Again, only the constructor can set
    // this value.
    private void setParameter(Integer parameter) {
        this.parameter = parameter;
    }

    // Helper for returning an event parameter.
    public Integer getParameter() {
        return this.parameter;
    }

    // Sets and gets the priority of this event.
    public void setPriority(int priority) throws PriorityAwareException {
        // Verify dates are in future.
        if (priority < 0) {
            throw new PriorityAwareException("Invalid timestamp.");
        }
        this.priority = priority;
    }

    // Gets the priority.
    public int getPriority() {
        return this.priority;
    }
}
