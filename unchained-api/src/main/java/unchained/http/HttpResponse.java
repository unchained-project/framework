package unchained.http;

import unchained.web.Response;

/**
 *
 */
public interface HttpResponse extends Response<HttpRequest, HttpResponse>, HttpBodyWriter {

    /**
     *
     * @return
     */
    int status();

    /**
     *
     * @return
     */
    String statusText();

    /**
     *
     * @param status
     * @return
     */
    HttpResponse status(int status);

    /**
     *
     * @param status
     * @param text
     * @return
     */
    HttpResponse status(int status, String text);

}
