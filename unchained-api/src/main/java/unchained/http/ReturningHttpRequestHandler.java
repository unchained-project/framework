package unchained.http;

import unchained.web.RequestHandler;

import java.util.concurrent.CompletableFuture;

public interface ReturningHttpRequestHandler extends RequestHandler<BodyReader, BodyWriter, HttpRequest, HttpResponse> {

    Object handle(HttpRequest request);

    @Override
    default CompletableFuture<Void> execute(HttpRequest input, HttpResponse output) throws Exception {
        return CompletableFuture
            .allOf(input.payload(), output.payload())
            .thenRunAsync(() -> output.payload().join().set(handle(input)));
    }

}
