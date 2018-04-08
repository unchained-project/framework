package unchained;


/**
 * This interface encapsulates the lifecycle of a general process.
 */
public interface Lifecycle {

    /**
     * Indicates if the lifecycle is started or not.
     *
     * @return {@code true} if this lifecycle has already been started.
     */
    boolean started();

    /**
     * Indicates if the lifecycle is terminated or not.
     *
     * @return {@code true} if this lifecycle has already been terminated.
     */
    boolean stopped();

}
