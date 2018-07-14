package unchained.web;

import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * TODO: doc
 *
 * @param <O>
 */
public interface PayloadWriter<O extends Response<? extends Request, O>> {

    /**
     * TODO: doc
     *
     * @return
     */
    OutputStream asStream();

    /**
     * TODO: doc
     *
     * @param charset
     * @return
     */
    Writer asWriter(Charset charset);

    /**
     * TODO: doc
     *
     * @param payload
     * @return
     */
    O write(byte[] payload);

    /**
     * TODO: doc
     *
     * @param payload
     * @return
     */
    O write(String payload, Charset charset);

    /**
     * TODO: doc
     *
     * @param payload
     * @return
     */
    @SuppressWarnings("unchecked")
    O write(String payload);

    /**
     * TODO: doc
     *
     * @param payload
     * @param <E>
     * @return
     */
    <E> O write(E payload);

    /**
     * TODO: doc
     *
     * @return
     */
    O done();

}
