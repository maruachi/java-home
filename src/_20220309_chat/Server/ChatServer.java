package _20220309_chat.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.HashMap;

public class ChatServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(7777);

            new Thread(new ClientListener(serverSocket)).start();

            BufferedReader reader = toReader(System.in);

            while (true) {
                String line = reader.readLine();
                int usernameIndex = line.indexOf(' ');
                if (usernameIndex == -1) {
                    System.out.println("'아이디 메세지' 형식으로 입력해주세요.");
                }
                String username = line.substring(0, usernameIndex);
                String message = line.substring(usernameIndex + 1);

                if ("all".equals(username)) {
                    ClientHandler.broadcast(message);
                    continue;
                }

                if (!ClientHandler.isChatUser(username)) {
                    System.out.println("아이디가 존재하지 않습니다.");
                    continue;
                }

                ClientHandler.sendTo(username, message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedReader toReader(InputStream inputStream) {
        BufferedInputStream bis = new BufferedInputStream(inputStream, 8192);
        InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
        return new BufferedReader(isr, 8192);
    }

    private static OutputStreamWriter toWriter(OutputStream outputStream) {
        BufferedOutputStream bos = new BufferedOutputStream(outputStream, 8192);
        return new OutputStreamWriter(bos, StandardCharsets.UTF_8);
    }

}
