package _20230421_tag;

public class CommandDto {
    private final Command command;
    private final int numArgument;

    public CommandDto(Command command, int numArgument) {
        this.command = command;
        this.numArgument = numArgument;
    }

    public Command getCommand() {
        return command;
    }

    public int getNumArgument() {
        return numArgument;
    }
}
