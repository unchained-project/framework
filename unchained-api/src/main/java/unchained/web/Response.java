package unchained.web;

import unchained.Output;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @param <S>
 * @param <T>
 * @param <I>
 */
public interface Response<S, T, I extends Request<S>> extends Output<RequestLifecycle, RequestContext, I> {

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
