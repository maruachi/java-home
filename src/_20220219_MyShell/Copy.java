package _20220219_MyShell;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Copy implements Command{
    private final String sourceFilename;
    private final String targetFilename;

    public Copy(String sourceFilename, String targetFilename) throws NotCreateCommandException {
        if (sourceFilename == null || targetFilename == null) {
            throw new NotCreateCommandException();
        }
        this.sourceFilename = sourceFilename;
        this.targetFilename = targetFilename;
    }

    @Override
    public void execute() {
        if (!Files.exists(Paths.get(sourceFilename))) {
            System.out.println("No source file");
            return;
        }

        if (Files.exists(Paths.get(targetFilename))) {
            System.out.println("There is already target file");
            return;
        }

        try (FileInputStream fis = new FileInputStream(sourceFilename);
            FileOutputStream fos = new FileOutputStream(targetFilename)) {
            IO.bufferedAllByteTransfer(fis, fos);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
