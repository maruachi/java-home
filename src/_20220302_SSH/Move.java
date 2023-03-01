package _20220302_SSH;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Move extends Command {
    private final String deleteFilename;

    public Move(InputStream inputStream, OutputStream outputStream, String deleteFilename) {
        super(inputStream, outputStream);
        this.deleteFilename = deleteFilename;
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
            return new Move(inputStream, outputStream, targetFilename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new EmptyCommand();
        }
    }

    @Override
    public void execute() {
        super.execute();
        try {
            Files.delete(Paths.get(deleteFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
