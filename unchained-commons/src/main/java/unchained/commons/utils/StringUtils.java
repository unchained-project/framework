package unchained.commons.utils;

import unchained.commons.annotation.UtilityClass;
import unchained.commons.error.UtilityClassInstantiationException;

import java.util.Arrays;
import java.util.Locale;

@UtilityClass
public class StringUtils {

    private StringUtils() {
        throw new UtilityClassInstantiationException(getClass());
    }

    public static String toString(Object value) {
        if (value == null) {
            return "null";
        } else if (value.getClass().isArray()) {
            return Arrays.toString((Object[]) value);
        } else {
            return String.valueOf(value);
        }
    }

    public static String toLanguageTag(Locale locale) {
        return locale.getLanguage() + (locale.getCountry() != null && !locale.getCountry().isEmpty()
                ? "-" + locale.getCountry()
                : "");
    }
}
