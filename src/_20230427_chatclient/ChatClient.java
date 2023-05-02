package _20230427_chatclient;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ChatClient {

    public static final int BUFFER_SIZE = 8129;
    public static final Charset UTF_8 = StandardCharsets.UTF_8;
    public static final String HOST = "192.168.0.55";

    public static void main(String[] args) {
        try (Socket socket = createSocket();) {
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
            throw new RuntimeException(ioException);
        }
    }

    private static Socket createSocket() {
        try {
            return new Socket(HOST, 7777);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static OutputStreamWriter toWriter(OutputStream outputStream) throws IOException {
        OutputStream bos = new BufferedOutputStream(outputStream, BUFFER_SIZE);
        return new OutputStreamWriter(bos, UTF_8);
    }

    private static BufferedReader toReader(InputStream inputStream) throws IOException {
        InputStream bis = new BufferedInputStream(inputStream, BUFFER_SIZE);
        InputStreamReader isr = new InputStreamReader(bis, UTF_8);
        return new BufferedReader(isr, BUFFER_SIZE);
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
