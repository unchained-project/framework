package unchained.http;

import unchained.Middleware;
import unchained.web.RequestContext;
import unchained.web.RequestLifecycle;

public interface HttpRouteDefiner<T> {

    <N extends Middleware<RequestLifecycle, RequestContext, HttpRequest, HttpResponse, ?>> T use(N first, N... rest);

    default T use(HttpRequestHandler first, HttpRequestHandler... rest) {
        return use((Middleware<RequestLifecycle, RequestContext, HttpRequest, HttpResponse, ?>) first, rest);
    }

    default T use(ReturningHttpRequestHandler first, ReturningHttpRequestHandler... rest) {
        return use((Middleware<RequestLifecycle, RequestContext, HttpRequest, HttpResponse, ?>) first, rest);
    }

    <N extends Middleware<RequestLifecycle, RequestContext, HttpRequest, HttpResponse, ?>> T use(String pattern, N first, N... rest);

    default T use(String pattern, HttpRequestHandler first, HttpRequestHandler... rest) {
        return use(pattern, (Middleware<RequestLifecycle, RequestContext, HttpRequest, HttpResponse, ?>) first, rest);
    }

    <N extends Middleware<RequestLifecycle, RequestContext, HttpRequest, HttpResponse, ?>> T all(String pattern, N first, N... rest);

    default T all(String pattern, HttpRequestHandler first, HttpRequestHandler... rest) {
        return all(pattern, (Middleware<RequestLifecycle, RequestContext, HttpRequest, HttpResponse, ?>) first, rest);
    }

    default T all(String pattern, ReturningHttpRequestHandler first, ReturningHttpRequestHandler... rest) {
        return all(pattern, (Middleware<RequestLifecycle, RequestContext, HttpRequest, HttpResponse, ?>) first, rest);
    }

    <N extends Middleware<RequestLifecycle, RequestContext, HttpRequest, HttpResponse, ?>> T custom(String method, String pattern, N first, N... rest);

    default T custom(String method, String pattern, HttpRequestHandler first, HttpRequestHandler... rest) {
        return custom(method, pattern, (Middleware<RequestLifecycle, RequestContext, HttpRequest, HttpResponse, ?>) first, rest);
    }

    default T custom(String method, String pattern, ReturningHttpRequestHandler first, ReturningHttpRequestHandler... rest) {
        return custom(method, pattern, (Middleware<RequestLifecycle, RequestContext, HttpRequest, HttpResponse, ?>) first, rest);
    }

    default <N extends Middleware<RequestLifecycle, RequestContext, HttpRequest, HttpResponse, ?>> T get(String pattern, N first, N... rest) {
        return custom(Method.GET, pattern, first, rest);
    }

    default T get(String pattern, HttpRequestHandler first, HttpRequestHandler... rest) {
        return get(pattern, (Middleware<RequestLifecycle, RequestContext, HttpRequest, HttpResponse, ?>) first, rest);
    }

    default T get(String pattern, ReturningHttpRequestHandler first, ReturningHttpRequestHandler... rest) {
        return get(pattern, (Middleware<RequestLifecycle, RequestContext, HttpRequest, HttpResponse, ?>) first, rest);
    }

}
