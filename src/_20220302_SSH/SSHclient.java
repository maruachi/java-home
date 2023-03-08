package _20220302_SSH;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SSHclient implements Commandable {

    public static final int PORT = 7777;

    private final String ip;

    public SSHclient(String ip) {
        this.ip = ip;
    }

    @Override
    public void execute() {
        try {
            Socket socket = new Socket(ip, PORT);
            InputStream inputStream = socket.getInputStream();

            Thread thread = new Thread(()->{
                InputStream bis = new BufferedInputStream(inputStream, 8192);
                OutputStream bos = new BufferedOutputStream(System.out, 8192);
                byte[] buffer = new byte[1024];
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    byte[] exitCode = "exit".getBytes(StandardCharsets.UTF_8);

                    while (true) {
                        int len = bis.read(buffer);
                        if (len == -1) {
                            break;
                        }

                        if (isEquals(buffer, exitCode, len)) {
                            socket.close();
                            break;
                        }

                        bos.write(buffer);
                        bos.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            byte[] buffer = new byte[1024];
            try (OutputStream bos = new BufferedOutputStream(socket.getOutputStream(), 8192);){
                while (true) {
                    if (!thread.isAlive()) {
                        break;
                    }

                    if (inputStream.available() <= 0) {
                        Thread.sleep(100);
                        continue;
                    }

                    IoUtils.bufferedAllByteTransfer(System.in, socket.getOutputStream(), buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private boolean isEquals(byte[] buffer, byte[] exitCode, int len) {
        if (len != exitCode.length) {
            return false;
        }

        for (int i = 0; i < len; i++) {
            if (exitCode[i] != buffer[i]) {
                return false;
            }
        }

        return true;
    }
}
