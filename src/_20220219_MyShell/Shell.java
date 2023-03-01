package _20220219_MyShell;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Shell {
    private final static String BANNER_NAME = "src/_20220219_MyShell/banner.txt";

    private final InputStreamReader commandLineStream;

    public Shell(InputStreamReader commandLineStream) {
        this.commandLineStream = commandLineStream;
    }

    public static Shell createDefault() {
        BufferedInputStream bis = new BufferedInputStream(System.in, 1000);

        InputStreamReader commandLineStream = new InputStreamReader(bis, StandardCharsets.UTF_8);
        return new Shell(commandLineStream);
    }

    public void launch() {
        this.printBanner();

        while (true) {
            String commandLine = readCommandLine();
            if (commandLine == null) {
                continue;
            }

            String[] commandlineElements = commandLine.split("[ ]+");

            String commandStr = commandlineElements[0];
            int numArgs = commandlineElements.length - 1;

            Command command = new EmptyCommand();

            try {
                if (numArgs == 1 && "cat".equals(commandStr)) {
                    String filename = commandlineElements[1];
                    command = new Cat(filename);
                }

                if (numArgs == 2 && "cp".equals(commandStr)) {
                    String sourceFilename = commandlineElements[1];
                    String targetFilename = commandlineElements[2];
                    command = new Copy(sourceFilename, targetFilename);
                }

                if (numArgs == 2 && "mv".equals(commandStr)) {
                    String sourceFilename = commandlineElements[1];
                    String targetFilename = commandlineElements[2];
                    command = new Move(sourceFilename, targetFilename);
                }

                if (numArgs == 3 && "scp".equals(commandStr)) {
                    String ip = commandlineElements[1];
                    String serverFilename = commandlineElements[2];
                    String clientFilename = commandlineElements[3];
                    command = new SecureCopy(ip, serverFilename, clientFilename);
                }
            } catch (NotCreateCommandException notCreateCommandException) {
                notCreateCommandException.printStackTrace();
            }

            command.execute();
        }
    }

    private String readCommandLine() {
        char[] buffer = new char[1000];

        int len = 0;
        String commandLine = null;
        try {
            len = commandLineStream.read(buffer);
            if (len == -1) {
                return null;
            }
            commandLine = new String(buffer, 0, len-1);
        } catch (IOException ioException) {
            return null;
        }

        return commandLine;
    }

    public void printBanner() {
        try (FileInputStream fis = new FileInputStream(BANNER_NAME)) {
            IO.bufferedAllByteTransfer(fis, System.out);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.print("Not found banner");
        }
    }
}
