package _20220302_SSH;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Shell {
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public Shell(InputStream inputStream, OutputStream outputStream) {
        if (inputStream == null || outputStream == null) {
            throw new RuntimeException();
        }
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public Shell createDefault() {
        return new Shell(System.in, System.out);
    }

    public void launch() {
        printBanner();

        while (true) {
            String readCommandLine = readCommandLine();
            if (readCommandLine == null) {
                continue;
            }

            String[] commandLineElements = readCommandLine.split("[ ]+");
            int numCommandLineElements = commandLineElements.length;

            Commandable command = null;

            if (numCommandLineElements == 2 && "cat".equals(commandLineElements[0])) {
                String filename = commandLineElements[1];
                command = new Cat2(filename, outputStream);
            }

            if (numCommandLineElements == 3 && "cp".equals(commandLineElements[0])) {

            }

            if (numCommandLineElements == 3 && "mv".equals(commandLineElements[0])) {

            }

            if (numCommandLineElements == 4 && "scp".equals(commandLineElements[0])) {

            }

            if (numCommandLineElements == 4 && "ssh".equals(commandLineElements[0])) {

            }

            command.execute();
        }
    }

    private void printBanner() {
        Commandable cat = new Cat2("src/_20220302_SSH/banner.txt", outputStream);
        cat.execute();
    }

    private String readCommandLine() {
        InputStream bis = new BufferedInputStream(inputStream, 8192);
        InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
        OutputStream bos = new BufferedOutputStream(outputStream, 8192);
        OutputStreamWriter osw = new OutputStreamWriter(bos, StandardCharsets.UTF_8);

        char[] buffer = new char[1000];

        try {
            int len = isr.read(buffer);
            if (len == -1) {
                return null;
            }

            if (buffer[len-1] != '\n') {
                return null;
            }

            return new String(buffer, 0, len-1);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
