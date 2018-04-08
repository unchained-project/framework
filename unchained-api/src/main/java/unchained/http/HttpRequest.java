package unchained.http;

import unchained.web.Request;

import java.net.InetSocketAddress;

public interface HttpRequest extends Request<BodyReader> {

    /**
     * @return the HTTP method/verb associated with this request.
     */
    String method();

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

}
