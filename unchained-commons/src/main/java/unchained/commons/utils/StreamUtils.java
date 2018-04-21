package unchained.commons.utils;

import unchained.commons.annotation.UtilityClass;
import unchained.commons.error.FrameworkIOException;
import unchained.commons.error.UtilityClassInstantiationException;
import unchained.commons.io.ResettableInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@UtilityClass
public class StreamUtils {

    public StreamUtils() {
        throw new UtilityClassInstantiationException(getClass());
    }

    public static byte[] readBytes(InputStream stream) {
        return readBytes(stream, -1);
    }

    public static byte[] readBytes(InputStream stream, int limit) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        final int bufferSize = 1024;
        final byte[] buffer = new byte[bufferSize];
        int read;
        try {
            while ((read = stream.read(buffer, 0, limit >= 0 ? Math.min(bufferSize, limit) : bufferSize)) > 0) {
                output.write(buffer, 0, read);
                if (limit >= 0) {
                    limit -= read;
                }
            }
        } catch (IOException e) {
            throw new FrameworkIOException("Failed to read from the input stream", e);
        }
        return output.toByteArray();
    }

    public static InputStream resettable(InputStream inputStream) {
        if (inputStream instanceof ResettableInputStream) {
            return inputStream;
        }
        return new ResettableInputStream(inputStream);
    }

    public static <O extends OutputStream> O copy(InputStream inputStream, O outputStream, int bufferSize) {
        final byte[] buffer = new byte[bufferSize];
        try {
            while (true) {
                final int bytes;
                try {
                    bytes = inputStream.read(buffer);
                } catch (IOException e) {
                    throw new FrameworkIOException("Failed to read from the input", e);
                }
                if (bytes <= 0) {
                    break;
                }
                try {
                    outputStream.write(buffer, 0, bytes);
                } catch (IOException e) {
                    throw new FrameworkIOException("Failed to write to the output", e);
                }
            }
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                //noinspection ThrowFromFinallyBlock
                throw new FrameworkIOException("Failed to close resources", e);
            }
        }
        return outputStream;
    }

}
