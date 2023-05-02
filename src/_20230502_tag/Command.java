package _20230502_tag;

public enum Command {
    CREATE("create"), EXECUTE("execute");

    private final String value;

    Command(String value) {
        this.value = value;
    }

    public static Command create(String value) {
        for (Command command : Command.values()) {
            if (command.value.equals(value)) {
                return command;
            }
        }

        throw new RuntimeException();
    }
}
