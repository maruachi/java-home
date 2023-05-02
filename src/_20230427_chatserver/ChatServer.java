package _20230427_chatserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatServer {

    public static final int BUFFER_SIZE = 8192;

    public static void main(String[] args) {
        ServerSocket serverSocket = createServerSocket();

        try (Socket socket = serverSocket.accept()) {
            BufferedReader serverReader = toReader(socket.getInputStream());
            Writer clientWriter = toWriter(System.out);
            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        transferMessageLine(serverReader, clientWriter);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            thread.start();

            //입력한 거 전송
            BufferedReader clientReader = toReader(System.in);
            Writer serverWriter = toWriter(socket.getOutputStream());
            while (true) {
                try {
                    transferMessageLine(clientReader, serverWriter);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        } catch (IOException ioException) {
            throw new RuntimeException();
        }
    }

    private static OutputStreamWriter toWriter(OutputStream outputStream) throws IOException {
        OutputStream bos = new BufferedOutputStream(outputStream, BUFFER_SIZE);
        return new OutputStreamWriter(bos, StandardCharsets.UTF_8);
    }

    private static BufferedReader toReader(InputStream inputStream) throws IOException {
        InputStream bis = new BufferedInputStream(inputStream, BUFFER_SIZE);
        InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
        return new BufferedReader(isr, BUFFER_SIZE);
    }

    private static ServerSocket createServerSocket() {
        try {
            return new ServerSocket(7777);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void transferMessageLine(BufferedReader reader, Writer writer) throws IOException {
        String messageLine = reader.readLine();
        if (messageLine == null) {
            return;
        }
        writer.write(messageLine);
        writer.write('\n');
        writer.flush();
    }
}
