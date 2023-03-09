package _20220309_chat.Client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

public class ChatClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("192.168.1.197", 7777);){
            BufferedReader clientInputStream = toReader(System.in);
            Writer serverOutputStream = toWriter(socket.getOutputStream());
            BufferedReader serverInputStream = toReader(socket.getInputStream());

            char[] buffer = new char[1024];
            int failCount = 0;
            while (true) {
                if (failCount >= 2) {
                    System.out.println("2회 이상 실패하면 프로그램이 종료됩니다.");
                    socket.close();
                    return;
                }

                String loginLine = clientInputStream.readLine();
                if (loginLine == null) {
                    continue;
                }

                serverOutputStream.write(loginLine);
                serverOutputStream.write("\n");
                serverOutputStream.flush();
                System.out.println("로그인 시도 중입니다.");

                String response = serverInputStream.readLine();
                if (response == null) {
                    failCount++;
                    continue;
                }


                if ("FAIL".equals(response)) {
                    failCount++;
                    System.out.println("접속 실패... 계정과 아이디를 다시 한번 확인해주세요");
                    System.out.println(failCount);
                    continue;
                }




                if ("SUCCESS".equals(response)) {
                    System.out.println("접속 성공");

                    new Thread(new ServerListener(socket)).start();

                    while (true) {
                        String messageLine = clientInputStream.readLine();
                        serverOutputStream.write(messageLine);
                        serverOutputStream.write('\n');
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

    private static BufferedReader toReader(InputStream inputStream) {
        BufferedInputStream bis = new BufferedInputStream(inputStream, 8192);
        InputStreamReader reader = new InputStreamReader(bis, StandardCharsets.UTF_8);
        return new BufferedReader(reader, 8192);
    }
}
