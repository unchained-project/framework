package unchained.http;

import unchained.web.RequestHandler;

import java.util.concurrent.CompletableFuture;

public interface HttpRequestHandler extends RequestHandler<BodyReader, BodyWriter, HttpRequest, HttpResponse> {

    void handle(HttpRequest request, HttpResponse response);

    @Override
    default CompletableFuture<Void> execute(HttpRequest input, HttpResponse output) throws Exception {
        return CompletableFuture
            .allOf(input.payload(), output.payload())
                .thenRunAsync(() -> handle(input, output));
    }

}
