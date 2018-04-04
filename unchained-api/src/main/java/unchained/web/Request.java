package unchained.web;

import unchained.Input;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * TODO: doc
 *
 * @param <T>
 */
public interface Request<T> extends Input<RequestLifecycle, RequestContext> {

    /**
     * TODO: doc
     *
     * @return
     */
    String uri();

    /**
     * TODO: doc
     *
     * @param uri
     */
    void uri(String uri);

    /**
     * TODO: doc
     *
     * @param name
     * @return
     */
    boolean hasHeader(String name);

    /**
     *
     * @return
     */
    Map<String, ?> headers();

    /**
     * TODO: doc
     *
     * @return
     */
    Set<String> headerNames();

    /**
     * TODO: doc
     *
     * @param name
     * @param <E>
     * @return
     */
    <E> E header(String name);

    /**
     * TODO: doc
     *
     * @param name
     * @param type
     * @param <E>
     * @return
     */
    <E> E header(String name, Class<E> type);

    /**
     * TODO: doc
     *
     * @param name
     * @param value
     */
    <E> E header(String name, E value);

    /**
     * TODO: doc
     *
     * @return
     */
    CompletableFuture<T> payload();

    /**
     * TODO: doc
     *
     * @param payload
     */
    void payload(T payload);

}
