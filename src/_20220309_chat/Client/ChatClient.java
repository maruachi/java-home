package _20220309_chat.Client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

public class ChatClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("192.168.0.56", 7777);){
            Reader clientInputStream = toReader(System.in);
            Writer serverOutputStream = toWriter(socket.getOutputStream());
            Reader serverInputStream = toReader(socket.getInputStream());

            int failCount = 0;
            char[] buffer = new char[1024];
            while (true) {
                if (failCount >= 2) {
                    return;
                }

                int len = clientInputStream.read(buffer);
                if (len == -1) {
                    break;
                }

                String string = new String(buffer, 0, len);
                int newLineIndex = string.indexOf('\n');
                if (newLineIndex == -1) {
                    failCount++;
                    continue;
                }

                String loginLine = string.substring(0, newLineIndex);

                serverOutputStream.write(loginLine);
                serverOutputStream.write("\n");
                serverOutputStream.flush();
                System.out.println(MessageFormat.format("로그인 시도 중입니다.", loginLine));

                len = serverInputStream.read(buffer);
                if (len == -1) {
                    break;
                }

                newLineIndex = new String(buffer, 0, len).indexOf('\n');
                if (newLineIndex == -1) {
                    failCount++;
                    continue;
                }
                String response = new String(buffer, 0, newLineIndex);


                if ("FAIL".equals(response)) {
                    failCount++;
                    System.out.println("접속 실패...");
                    continue;
                }

                if ("SUCCESS".equals(response)) {
                    System.out.println("접속 성공");

                    new Thread(new ServerListener(socket)).start();

                    while (true) {
                        len = clientInputStream.read(buffer);
                        if (len == -1) {
                            return;
                        }
                        String messageLine = new String(buffer, 0, len);
                        serverOutputStream.write(messageLine);
                        serverOutputStream.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static OutputStreamWriter toWriter(OutputStream outputStream) {
        BufferedOutputStream bos = new BufferedOutputStream(outputStream, 8192);
        return new OutputStreamWriter(bos, StandardCharsets.UTF_8);
    }

    private static InputStreamReader toReader(InputStream inputStream) {
        BufferedInputStream bis = new BufferedInputStream(inputStream, 8192);
        return new InputStreamReader(bis, StandardCharsets.UTF_8);
    }
}
