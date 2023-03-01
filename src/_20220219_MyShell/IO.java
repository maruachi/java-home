package _20220219_MyShell;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class IO {
    public static final java.nio.charset.Charset UTF_8 = StandardCharsets.UTF_8;
    private final static int BUFFER_SIZE = 8192;

    public static Reader createReader(InputStream inputStream) {
        return new InputStreamReader(new BufferedInputStream(inputStream, 8192), UTF_8);
    }

    public static Writer createWriter(OutputStream outputStream) {
        return new OutputStreamWriter(new BufferedOutputStream(outputStream, 8192), UTF_8);
    }

    public static void bufferedAllByteTransfer(InputStream inputStream, OutputStream outputStream) {
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            BufferedInputStream bis = new BufferedInputStream(inputStream, BUFFER_SIZE);
            BufferedOutputStream bos = new BufferedOutputStream(outputStream, BUFFER_SIZE);
            while (true) {
                int len = bis.read(buffer);
                if (len == -1) {
                    break;
                }
                bos.write(buffer, 0, len);
            }
            bos.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
