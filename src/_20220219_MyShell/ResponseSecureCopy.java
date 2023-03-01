package _20220219_MyShell;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ResponseSecureCopy {
    public static final java.nio.charset.Charset UTF_8 = StandardCharsets.UTF_8;

    public static final int BUFFER_SIZE = 8192;

    public static Reader createReader(InputStream inputStream) {
        return new InputStreamReader(new BufferedInputStream(inputStream, 8192), UTF_8);
    }

    public static Writer createWriter(OutputStream outputStream) {
        return new OutputStreamWriter(new BufferedOutputStream(outputStream, 8192), UTF_8);
    }

    public static boolean endWith(String filePath, String str) {
        if (Files.notExists(Paths.get(filePath))) {
            return false;
        }

        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r");) {
            byte[] stringBytes = str.getBytes(StandardCharsets.UTF_8);

            long size = Files.size(Paths.get(filePath));
            int stringBytesLength = stringBytes.length;
            if (size < stringBytesLength) {
                return false;
            }
            raf.seek(size - stringBytesLength);

            byte[] buffer = new byte[stringBytesLength];
            int len = raf.read(buffer);
            if (len == -1) {
                return false;
            }

            System.out.println(str + "\n" + new String(buffer, 0, len, StandardCharsets.UTF_8) + "\n" + Arrays.equals(buffer, stringBytes));
            return Arrays.equals(buffer, stringBytes);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return false;
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

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(7777);){
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("서버 연결 성공");
                try (Reader reader = createReader(socket.getInputStream());
                     Writer writer = createWriter(socket.getOutputStream());
                ) {
                    // filename을 받는다
                    char[] cbuf = new char[1024];
                    int len = reader.read(cbuf);

                    if ('\n' != cbuf[len-1]) {
                        System.out.println("파일이름 수신 실패");
                        return;
                    }

                    String filename = new String(cbuf, 0, len - 1);

                    Path path = Paths.get(filename);
                    if (Files.exists(path)) {
                        writer.write("FAIL");
                        writer.flush();
                        System.out.println("파일이 이미 존재합니다.");
                        return;
                    }

                    writer.write("OK");
                    writer.flush();

                    String confirmToken = "--389jdfosgj0wwrjdsf";

                    len = reader.read(cbuf, 0, confirmToken.length());
                    String line = new String(cbuf, 0, len);

                    if (!line.equals(confirmToken)) {
                        writer.write("FAIL");
                        writer.flush();
                        return;
                    }

                    System.out.println("파일 생성중...");
                    try (FileOutputStream fos = new FileOutputStream(filename);
                    ) {
                        byte[] buffer = new byte[BUFFER_SIZE];
                        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream(), BUFFER_SIZE);
                        BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER_SIZE);
                        while (true) {
                            len = bis.read(buffer);
                            if (len == -1) {
                                break;
                            }

                            bos.write(buffer, 0, len);
                            bos.flush();

                            if (endWith(filename, confirmToken)) {
                                break;
                            }
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        Files.deleteIfExists(path);
                        writer.write("FAIL");
                        writer.flush();
                        return;
                    }
                    System.out.println("파일 생성 완료... 토큰 포함");

                    if (!endWith(filename, confirmToken)) {
                        Files.deleteIfExists(path);
                        writer.write("FAIL");
                        writer.flush();
                        return;
                    }

                    try (RandomAccessFile raf = new RandomAccessFile(filename, "rw");){
                        long size = Files.size(path);
                        byte[] confirmByteToken = confirmToken.getBytes(StandardCharsets.UTF_8);
                        int tokenLength = confirmByteToken.length;
                        raf.seek(size - tokenLength);
                        byte[] buffer = new byte[tokenLength];
                        len = raf.read(buffer);

                        raf.setLength(size - tokenLength);
                        writer.write("OK");
                        writer.flush();
                    } catch (IOException ioException) {
                        Files.deleteIfExists(path);
                        writer.write("FAIL");
                        writer.flush();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("클라이언트 연결 실패");
        }
    }
}
