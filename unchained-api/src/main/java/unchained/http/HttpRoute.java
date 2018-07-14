package unchained.http;

import unchained.web.Route;

public interface HttpRoute extends Route<HttpRequest, HttpResponse, HttpSelector, HttpRequestLine, HttpRoute>,
    HttpRouteDefiner<HttpRoute> {

}
