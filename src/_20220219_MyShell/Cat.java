package _20220219_MyShell;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Cat implements Command{
    private final String filename;

    public Cat(String filename) throws NotCreateCommandException {
        if (filename == null) {
            throw new NotCreateCommandException();
        }
        this.filename = filename;
    }

    @Override
    public void execute() {
        if (!Files.exists(Paths.get(filename))) {
            System.out.println("There is no such file");
            return;
        }

        try (FileInputStream fis = new FileInputStream(filename);) {
            IO.bufferedAllByteTransfer(fis, System.out);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
