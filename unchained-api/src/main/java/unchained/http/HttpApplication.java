package unchained.http;

import unchained.Application;
import unchained.web.RequestContext;
import unchained.web.RequestLifecycle;

/**
 * TODO: doc
 */
public interface HttpApplication extends Application<RequestLifecycle, RequestContext, HttpRequest, HttpResponse,
    HttpSelector, HttpApplication>, HttpRouteDefiner<HttpApplication> {

}
