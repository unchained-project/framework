package unchained.http;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

public interface BodyReader {

    InputStream asStream();

    byte[] asBytes();

    String asString();

    String asString(Charset charset);

    <E> E as(Class<E> type);

    <E> E as(Type type);

}
