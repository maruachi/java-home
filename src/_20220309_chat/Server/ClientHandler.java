package _20220309_chat.Server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.SignedObject;
import java.text.MessageFormat;
import java.util.HashMap;

public class ClientHandler implements Runnable {
    private static final HashMap<String, String> users = new HashMap<>();
    private static final HashMap<String, Socket> clientSockets = new HashMap<>();

    {
        users.put("gyu", "123");
        users.put("dong", "321");
    }

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = toReader(socket.getInputStream());
            Writer writer = toWriter(socket.getOutputStream());
            char[] buffer = new char[1000];
            while (true) {
                int len = reader.read(buffer);
                String loginLine = new String(buffer, 0, len-1);
                String[] loginLineElements = loginLine.split("[ ]+");
                String username = loginLineElements[0];
                String password = loginLineElements[1];
                System.out.println(MessageFormat.format("username: {0} password: {1}", username, password));
                System.out.println("클라이언트로 부터 로그인 시도가 들어왔습니다.");

                if (!isAuthenticated(username, password)) {
                    System.out.println("클라이언트 접속 실패했습니다.");
                    writer.write("FAIL");
                    writer.write("\n");
                    writer.flush();
                    continue;
                }

                System.out.println("클라이언트 접속 성공했습니다.");
                clientSockets.put(username, socket);
                writer.write("SUCCESS");
                writer.write("\n");
                writer.flush();

                Thread.sleep(500);

                writer.write("서버 입장에 성공하셨습니다.");
                writer.write('\n');
                writer.flush();

                Writer serverWriter = toWriter(new FileOutputStream(MessageFormat.format("{0}.log", username)));
                while (true) {
                    String messageLine = reader.readLine();
                    if (messageLine == null) {
                        writer.write("서버와 연결이 끊어졌습니다.");
                        writer.flush();
                        socket.close();
                        return;
                    }
                    serverWriter.write(messageLine);
                    serverWriter.write('\n');
                    serverWriter.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isAuthenticated(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
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

    public static boolean isChatUser(String username) {
        return users.containsKey(username);
    }

    public static void sendTo(String username, String message) {
        if (!isChatUser(username)) {
            return;
        }
        Socket socket = clientSockets.get(username);
        try {
            Writer writer = toWriter(socket.getOutputStream());
            writer.write(message);
            writer.write('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message) {
        for (String username : clientSockets.keySet()) {
            sendTo(username, message);
        }
    }
}
