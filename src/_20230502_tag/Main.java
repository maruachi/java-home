package _20230502_tag;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static final int BUFFER_SIZE = 8192;

    public static void main(String[] args) {
        List<String> lines = readLines(toReader(System.in));

        List<String[]> parsedLines = lines.stream()
                .map(line -> parseArguments(line))
                .collect(Collectors.toList());

        Set<Command> commandSet = new HashSet<>(Arrays.asList(Command.CREATE, Command.EXECUTE));
        List<Commandable> commands = parsedLines.stream()
                .map(lineElements -> createCommand(lineElements, commandSet))
                .collect(Collectors.toList());



        Queue<Tag> tags = new PriorityQueue<>(Tag.createDefaultTags());
        Set<Tag> usableTag = new HashSet<>();

        List<ResultDto> failDtos = commands.stream()
                .map(command -> command.run(tags, usableTag))
                .filter(resultDto -> resultDto.isFail())
                .collect(Collectors.toList());

        Map<Tag, Integer> failCounts = failDtos.stream()
                .collect(Collectors.toMap(r -> r.getTag(), r -> 1, Integer::sum));

    }

    private static List<String> readLines(BufferedReader _reader) {
        try (BufferedReader reader = _reader;) {
            int N = Integer.parseInt(reader.readLine());
            return IntStream.range(0, N)
                    .mapToObj(i -> {
                        try {
                            return reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static Commandable createCommand(String[] lineElements, Set<Command> commandSet) {
        if (lineElements.length == 0) {
            throw new RuntimeException();
        }
        Command command = Command.create(lineElements[0]);
        if (!commandSet.contains(command)) {
            throw new RuntimeException();
        }

        int numArgument = lineElements.length - 1;
        if (numArgument == 0 && command == Command.CREATE) {
            return new Create();
        }

        if (numArgument == 1 &&command == Command.EXECUTE) {
            return new Execute(new Tag(Integer.parseInt(lineElements[1])));
        }

        throw new RuntimeException();
    }

    private static String[] parseArguments(String line) {
        return Arrays.stream(line.split(" "))
                .filter(lineElement -> !lineElement.isEmpty())
                .toArray(String[]::new);
    }

    private static BufferedReader toReader(InputStream in) {
        BufferedInputStream bis = new BufferedInputStream(in, BUFFER_SIZE);
        InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
        return new BufferedReader(isr, BUFFER_SIZE);
    }
}
