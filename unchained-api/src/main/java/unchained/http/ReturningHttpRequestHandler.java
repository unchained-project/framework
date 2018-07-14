package unchained.http;

import unchained.web.RequestHandler;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface ReturningHttpRequestHandler extends RequestHandler<HttpRequest, HttpResponse> {

    Object handle(HttpRequest request);

    @Override
    default void handle(HttpRequest request, HttpResponse response) {
        response.write(handle(request));
    }

}
