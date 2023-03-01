package _20220302_SSH;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Cat extends Command {

    public Cat(InputStream inputStream, OutputStream outputStream) {
        super(inputStream, outputStream);
    }

    public Commandable createByFilename(String filename, OutputStream outputStream) {
        if (Files.notExists(Paths.get(filename))) {
            return new EmptyCommand();
        }

        try {
            InputStream inputStream = new FileInputStream(filename);
            return new Cat(inputStream, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new EmptyCommand();
        }
    }
}
