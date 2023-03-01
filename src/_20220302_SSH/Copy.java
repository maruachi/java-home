package _20220302_SSH;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Copy extends Command {

    public Copy(InputStream inputStream, OutputStream outputStream) {
        super(inputStream, outputStream);
    }

    public Commandable createByFilename(String sourceFilename, String targetFilename) {
        if (Files.notExists(Paths.get(sourceFilename))) {
            return new EmptyCommand();
        }

        if (Files.exists(Paths.get(targetFilename))) {
            return new EmptyCommand();
        }

        try {
            InputStream inputStream = new FileInputStream(sourceFilename);
            OutputStream outputStream = new FileOutputStream(targetFilename);
            return new Copy(inputStream, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new EmptyCommand();
        }
    }
}
