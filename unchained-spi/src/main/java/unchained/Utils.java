package unchained;

import static unchained.commons.assertion.Assert.assertThat;
import static unchained.commons.assertion.Assertions.isNotNull;

final class Utils {

    private Utils() {}

    static <O> O forceNotNull(O value, String name) {
        assertThat(value, "lifecycle", isNotNull());
        return value;
    }

}
