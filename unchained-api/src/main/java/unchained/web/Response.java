package unchained.web;

import unchained.Output;

import java.util.Map;
import java.util.Set;

/**
 * TODO: doc
 *
 * @param <I>
 */
public interface Response<I extends Request<I>, O extends Response<I, O>> extends
    Output<RequestLifecycle, RequestContext, I>, PayloadWriter<O> {

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
    <E> O header(String name, E value);

}
