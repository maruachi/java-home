package _20220302_SSH;

import java.io.*;
import java.net.Socket;

public class SecureCopy extends Command {
    public SecureCopy(InputStream inputStream, OutputStream outputStream) {
        super(inputStream, outputStream);
    }

    public Commandable create(String clientFilename, String serverFilename, String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);

            try (InputStream inputStream = new FileInputStream(clientFilename);
                 OutputStream outputStream = socket.getOutputStream()
            ) {

                return new SecureCopy(inputStream, outputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new EmptyCommand();
    }

    @Override
    public void execute() {
        super.execute();
    }
}
