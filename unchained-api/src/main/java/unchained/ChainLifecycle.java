package unchained;

/**
 * This interface encapsulates the lifecycle of an I/O operation as it is being handled by a chain.
 */
public interface ChainLifecycle extends Lifecycle {

    /**
     * Stops the execution of the chain from this point onward. The process will terminate immediately and the outcome
     * will be communicated back to the client.
     */
    void stop();

}
