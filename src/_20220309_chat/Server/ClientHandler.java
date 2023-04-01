package _20220309_chat.Server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public class ClientHandler implements Runnable {
    private static final HashMap<String, String> users = new HashMap<>();
    private static final HashMap<String, Socket> clientSockets = new HashMap<>();

    public static final DateTimeFormatter YYYY_MM_DD_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

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
            int failCount = 0;
            while (true) {
                if (failCount >= 2) {
                    System.out.println("2회 이상 실패하면 프로그램이 종료됩니다.");
                    socket.close();
                    return;
                }

                int len = reader.read(buffer);
                String loginLine = new String(buffer, 0, len-1);
                String[] loginLineElements = loginLine.split("[ ]+");
                if (loginLineElements.length != 2) {
                    System.out.println("클라이언트 접속 실패했습니다.");
                    loginResponse(writer, "FAIL");
                    failCount++;
                    continue;
                }
                String username = loginLineElements[0];
                String password = loginLineElements[1];
                System.out.println(MessageFormat.format("username: {0} password: {1}", username, password));
                System.out.println("클라이언트로 부터 로그인 시도가 들어왔습니다.");

                if (!isAuthenticated(username, password)) {
                    System.out.println("클라이언트 접속 실패했습니다.");
                    loginResponse(writer, "FAIL");
                    failCount++;
                    continue;
                }

                if (failCount >= 2) {
                    socket.close();
                    return;
                }

                System.out.println("클라이언트 접속 성공했습니다.");
                clientSockets.put(username, socket);
                loginResponse(writer, "SUCCESS");

                Thread.sleep(500);

                writer.write("서버 입장에 성공하셨습니다.");
                writer.write('\n');
                writer.flush();

                LocalDate localDate = LocalDate.now();

                String stringDate = YYYY_MM_DD_FORMATTER.format(localDate);
                Writer serverWriter = toWriter(new FileOutputStream(MessageFormat.format("{0}_{1}.log", username, stringDate),true));
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

    private void loginResponse(Writer writer, String response) throws IOException {
        writer.write(response);
        writer.write("\n");
        writer.flush();
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

        String stringDate = ClientHandler.YYYY_MM_DD_FORMATTER.format(LocalDate.now());
        try {
            Writer writer = toWriter(new FileOutputStream(MessageFormat.format("{0}_{1}.log", username, stringDate), true));
            writer.write(message);
            writer.write('\n');
            writer.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
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
