package unchained.web;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * TODO: doc
 *
 * @param <I>
 */
public interface PayloadReader<I extends Request<I>> {

    /**
     * TODO: doc
     *
     * @return
     */
    InputStream asStream();

    /**
     * TODO: doc
     *
     * @param charset
     * @return
     */
    Reader asReader(Charset charset);

    /**
     * TODO: doc
     *
     * @return
     */
    byte[] asBytes();

    /**
     * TODO: doc
     *
     * @return
     */
    String asString();

    /**
     * TODO: doc
     *
     * @param charset
     * @return
     */
    String asString(Charset charset);

    /**
     * TODO: doc
     *
     * @param type
     * @param <E>
     * @return
     */
    <E> E as(Class<E> type);

    /**
     * TODO: doc
     *
     * @param type
     * @param <E>
     * @return
     */
    <E> E as(Type type);

}
