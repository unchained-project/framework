package unchained.http;

import unchained.web.RequestHandler;

import java.util.concurrent.CompletableFuture;

public interface HttpRequestHandler extends RequestHandler<HttpRequest, HttpResponse> {

}
