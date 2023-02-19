package _20220219_MyShell;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Move implements Command{
    private final String sourceFilename;
    private final String targetFilename;

    public Move(String sourceFilename, String targetFilename) {
        if (sourceFilename == null || targetFilename == null) {
            System.out.println("filenames is null");
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

        try {
            Files.delete(Paths.get(sourceFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
