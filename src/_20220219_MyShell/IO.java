package _20220219_MyShell;

import java.io.*;

public class IO {
    private final static int BUFFER_SIZE = 8192;

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
