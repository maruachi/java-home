package _20220302_SSH;

import java.io.*;

public class Command implements Commandable{
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public Command(InputStream inputStream, OutputStream outputStream) {
        if (inputStream == null || outputStream == null) {
            throw new RuntimeException();
        }
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void execute() {
        OutputStream bos = new BufferedOutputStream(outputStream, 8192);

        byte[] buffer = new byte[8192];
        IoUtils.bufferedAllByteTransfer(inputStream, outputStream, buffer);
    }
}
