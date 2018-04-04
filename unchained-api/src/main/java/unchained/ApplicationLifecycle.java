package unchained;

/**
 * This interface encapsulates the lifecycle of an application.
 */
public interface ApplicationLifecycle extends Lifecycle {

    /**
     * TODO
     */
    void start();

    /**
     * Stops the execution of the application from this point onward. After calling this method, the application stops
     * processing incoming requests. The implementation has the liberty to release its allocated resources. Hence, this
     * action might be irreversible.
     */
    void stop();

}
