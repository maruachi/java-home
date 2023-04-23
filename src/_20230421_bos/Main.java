package _20230421_bos;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {

    public static final int BUFFER_SIZE = 8192;

    public static void main(String[] args) throws IOException {
        String string = "hello world";

        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

        InputStream inputStream = new ByteArrayInputStream(bytes);
        InputStream bis = new BufferedInputStream(inputStream, 8192);

        OutputStream bos = new BufferedOutputStream(System.out, 8192);

        byte[] buffer = new byte[8192];

        while (true) {
            int len = inputStream.read(buffer);
            if (len == -1) {
                bos.flush();
                break;
            }
            bos.write(buffer, 0, len);
        }
    }
}
