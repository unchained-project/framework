package unchained;

import unchained.contract.Factory;
import unchained.http.HttpApplication;
import unchained.http.HttpRoute;
import unchained.web.Route;

public interface Unchained {

    static Configuration emptyConfiguration() {
        // TODO: implement
        return null;
    }

    static MutableConfiguration newConfiguration() {
        return Factory.forType(MutableConfiguration.class).create();
    }

    static HttpApplication newHttpApplication(Configuration configuration) {
        return newApplication(HttpApplication.class, configuration);
    }

    static <T extends Application> T newApplication(Class<T> type, Configuration configuration) {
        return Factory.forType(type).create(configuration);
    }

    static <T extends Route> T newRoute(Class<T> type) {
        return Factory.forType(type).create();
    }

    static HttpRoute newHttpRoute() {
        return newRoute(HttpRoute.class);
    }

}
