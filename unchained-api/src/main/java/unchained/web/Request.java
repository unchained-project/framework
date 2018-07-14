package unchained.web;

import unchained.Input;

import java.util.Map;
import java.util.Set;

/**
 * TODO: doc
 */
public interface Request<I extends Request<I>> extends Input<RequestLifecycle, RequestContext>, PayloadReader<I> {

    /**
     * TODO: doc
     *
     * @return
     */
    String target();

    /**
     * TODO: doc
     *
     * @param target
     */
    I target(String target);

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
    <E> I header(String name, E value);

}
