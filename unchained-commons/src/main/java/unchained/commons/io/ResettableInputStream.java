package unchained.commons.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResettableInputStream extends InputStream {

    private final ByteArrayOutputStream buffer;
    private InputStream delegate;
    private InputStream target;
    private long actual;
    private long current;

    public ResettableInputStream(InputStream delegate) {
        this.delegate = delegate;
        target = delegate;
        buffer = new ByteArrayOutputStream();
        actual = 0;
        current = 0;
    }

    @Override
    public int read() throws IOException {
        if (current == actual) {
            target = delegate;
        }
        final int read = target.read();
        if (read < 0) {
            return -1;
        }
        if (current == actual) {
            buffer.write(read);
            actual++;
        }
        current++;
        return read;
    }

    @Override
    public synchronized void reset() throws IOException {
        if (actual == 0) {
            return;
        }
        current = 0;
        target = new ByteArrayInputStream(buffer.toByteArray());
    }

}
