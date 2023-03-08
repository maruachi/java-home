package _20220302_SSH;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SecureShellClient extends Command  {

    public SecureShellClient(InputStream inputStream, OutputStream outputStream) {
        super(inputStream, outputStream);
    }

    public static Commandable create(String ip, String account, String password) {
        try {
            Socket socket = new Socket(ip, 7777);
            OutputStream outputStream = socket.getOutputStream();
            if (!authenticate(account, password)) {
                return new EmptyCommand();
            }

            return new SecureShellClient(System.in, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return new EmptyCommand();
        }
    }

    private static boolean authenticate(String account, String password) {
        return true;
    }
}
