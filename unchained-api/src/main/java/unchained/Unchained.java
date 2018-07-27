package unchained;

import unchained.contract.Factory;
import unchained.http.HttpApplication;
import unchained.http.HttpRoute;
import unchained.web.Route;

public final class Unchained {

    private Unchained() { }

    public static Configuration emptyConfiguration() {
        return Factory.forType(Configuration.class).create();
    }

    public static MutableConfiguration newConfiguration() {
        return Factory.forType(MutableConfiguration.class).create();
    }

    public static HttpApplication newHttpApplication(Configuration configuration) {
        return newApplication(HttpApplication.class, configuration);
    }

    public static <T extends Application> T newApplication(Class<T> type, Configuration configuration) {
        return Factory.forType(type).create(configuration);
    }

    public static <T extends Route> T newRoute(Class<T> type) {
        return Factory.forType(type).create();
    }

    public static HttpRoute newHttpRoute() {
        return newRoute(HttpRoute.class);
    }

    static {
        try {
            // Workaround to ensure class initializer is executed.
            ClassLoader.getSystemClassLoader().loadClass("unchained.Utils")
                .getDeclaredField("environment").get(null);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

}
