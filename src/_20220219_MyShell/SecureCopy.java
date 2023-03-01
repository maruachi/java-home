package _20220219_MyShell;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class SecureCopy implements Command{
    public static final int PORT = 7777;

    private final String ip;
    private final String serverFilename;
    private final String clientFilename;

    public SecureCopy(String ip, String serverFilename, String clientFilename) {
        if (ip == null || serverFilename == null || clientFilename == null) {
            throw new NotCreateCommandException();
        }
        System.out.println(ip + ", " + serverFilename + ", " + clientFilename);
        this.ip = ip;
        this.serverFilename = serverFilename;
        this.clientFilename = clientFilename;
    }



    @Override
    public void execute() {
        // client file 있어?
        if (Files.notExists(Paths.get(clientFilename))) {
            System.out.println("전송할 피알이 존재하지 않습니다. 파일 이름을 다시 확인해주세요");
            return;
        }

        try (Socket socket = new Socket(ip, PORT)) {
            try (Writer writer = IO.createWriter(socket.getOutputStream());
                 Reader reader = IO.createReader(socket.getInputStream())){
                writer.write(serverFilename + "\n");
                writer.flush();

                char[] buffer = new char[1024];
                int len = reader.read(buffer);

                String fileExistAckResponse = new String(buffer, 0, len);
                if ("FAIL".equalsIgnoreCase(fileExistAckResponse)) {
                    System.out.println("서버에 파일이 이미 존재합니다. 파일 이름을 다시 확인해주세요.");
                    return;
                }

                if ("OK".equalsIgnoreCase(fileExistAckResponse)) {
                    InputStream inputStream = new FileInputStream(clientFilename);
                    OutputStream outputStream = socket.getOutputStream();
                    String beginLine = "--389jdfosgj0wwrjdsf";

                    writer.write(beginLine);
                    writer.flush();
                    IO.bufferedAllByteTransfer(inputStream, outputStream);

                    String endLine = "--389jdfosgj0wwrjdsf";
                    writer.write(endLine);
                    writer.flush();

                    len = reader.read(buffer);
                    String fileTransferAckResponse = new String(buffer, 0, len);
                    if ("OK".equalsIgnoreCase(fileTransferAckResponse)) {
                        System.out.println("파일 전송이 완료됐습니다.");
                        return;
                    }

                    System.out.println("파일 전송에 실패했습니다.");
                }

                System.out.println(MessageFormat.format("서버 통신 실패. 통신코드: {0}", fileExistAckResponse));
            } catch (IOException ioException) {
                ioException.printStackTrace();
                return;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("서버 연결 실패.");
            return;
        }
    }
}
