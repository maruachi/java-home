package _20230421_bos;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {

    public static final int BUFFER_SIZE = 8192;

    public static void main(String[] args) throws IOException {
        String string = "hello world";

        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

        InputStream inputStream = new ByteArrayInputStream(bytes);
        InputStream bis = new BufferedInputStream(inputStream, BUFFER_SIZE);
        OutputStream bos = new BufferedOutputStream(System.out, BUFFER_SIZE);

        byte[] buffer = new byte[BUFFER_SIZE];
        while (true) {
            int len = bis.read(buffer);
            if (len == -1) {
                bos.flush();
                break;
            }
            bos.write(buffer, 0, len);
        }

    }
}
