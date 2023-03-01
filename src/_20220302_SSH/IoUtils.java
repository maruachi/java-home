package _20220302_SSH;

import java.io.*;

public class IoUtils {
    public static void bufferedAllByteTransfer(InputStream inputStream, OutputStream outputStream, byte[] buffer) {
        OutputStream bos = new BufferedOutputStream(outputStream, 8192);

        try (InputStream bis = new BufferedInputStream(inputStream, 8192);){
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
