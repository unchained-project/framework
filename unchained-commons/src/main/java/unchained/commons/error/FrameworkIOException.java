package unchained.commons.error;

import java.io.IOException;

public class FrameworkIOException extends FrameworkException {

    public FrameworkIOException(String message, IOException cause) {
        super(message, cause);
    }

}
