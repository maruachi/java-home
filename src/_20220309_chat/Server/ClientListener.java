package _20220309_chat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientListener implements Runnable {
    private final ServerSocket serverSocket;

    public ClientListener(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("클라이언트가 연결되었습니다.");

                new Thread(new ClientHandler(socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
