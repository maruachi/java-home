package _20220302_SSH;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SecureShellServer extends Shell {

    public SecureShellServer(InputStream inputStream, OutputStream outputStream) {
        super(inputStream, outputStream);
    }

    public static SecureShellServer create() {
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            Socket socket = serverSocket.accept();

            return new SecureShellServer(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
