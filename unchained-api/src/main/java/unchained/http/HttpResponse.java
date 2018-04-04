package unchained.http;

import unchained.web.Response;

public interface HttpResponse extends Response<BodyReader, BodyWriter, HttpRequest> {

    int status();

    String statusText();

    HttpResponse status(int status, String text);

}
