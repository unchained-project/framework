package unchained.http;

import unchained.web.Request;

import java.net.InetSocketAddress;

public interface HttpRequest extends Request<HttpRequest>, HttpBodyReader {

    /**
     * @return the HTTP method/verb associated with this request.
     */
    String method();

    /**
     * TODO: doc
     *
     * @param method
     * @return
     */
    HttpRequest method(String method);

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
     * @return
     */
    HttpRequest uri(String uri);

    /**
     * @return the HTTP scheme used to run this request (e.g. https).
     */
    String scheme();

    /**
     * @return the protocol on which this request was handled (e.g. HTTP 1.1).
     */
    String protocol();

    /**
     * @return the remote address of the client.
     */
    InetSocketAddress remote();

    /**
     * TODO: doc
     *
     * @return
     */
    @Override
    default String target() {
        return uri();
    }

    /**
     * TODO: doc
     *
     * @param target
     * @return
     */
    @Override
    default HttpRequest target(String target) {
        return uri(target);
    }

}
