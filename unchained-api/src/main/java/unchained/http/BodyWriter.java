package unchained.http;

import java.io.OutputStream;

public interface BodyWriter {

    OutputStream asStream();

    void set(byte[] body);

    void set(String body);

    <E> void set(E body);

    void done();

}
