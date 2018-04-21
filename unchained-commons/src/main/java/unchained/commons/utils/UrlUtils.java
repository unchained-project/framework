package unchained.commons.utils;

import unchained.commons.annotation.UtilityClass;
import unchained.commons.error.UtilityClassInstantiationException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@UtilityClass
public class UrlUtils {

    private UrlUtils() {
        throw new UtilityClassInstantiationException(getClass());
    }

    /**
     * Returns a URL which starts with a slash and has no trailing slashes.
     *
     * @param url the URL to normalize
     * @return the normalized URL
     */
    public static String normalizeContextPath(String url) {
        return (url != null ? (url.startsWith("/") ? url : "/" + url) : "")
                .replaceFirst("^/+", "/")
                .replaceFirst("/+$", "")
                .replaceFirst("^$", "/");
    }

    public static String concat(String prefix, String continuation) {
        return normalizeContextPath(normalizeContextPath(prefix)
                + normalizeContextPath(continuation));
    }

    public static boolean isEmpty(String url) {
        return url == null || url.matches("/*");
    }

    public static String encodeComponent(String component) {
        return encodeComponent(component, "UTF-8");
    }

    public static String encodeComponent(String component, final String encoding) {
        try {
            return URLEncoder.encode(component, encoding)
                      .replaceAll("%28", "(")
                      .replaceAll("%29", ")")
                      .replaceAll("\\+", "%20")
                      .replaceAll("%27", "'")
                      .replaceAll("%21", "!")
                      .replaceAll("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
