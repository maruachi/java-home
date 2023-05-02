package _20230501_tag;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static final int BUFFER_SIZE = 8192;

    public static void main(String[] args) {
        List<Commandable> commands = createCommands(toReader(System.in));

        Queue<Tag> tags = new PriorityQueue<>(Tag.createDefaultTags());
        Set<Tag> usableTag = new HashSet<>();

        List<ResultDto> failDtos = commands.stream()
                .map(command -> command.run(tags, usableTag))
                .filter(resultDto -> resultDto.isFail())
                .collect(Collectors.toList());

        Map<Tag, Integer> failCounts = failDtos.stream()
                .collect(Collectors.toMap(r -> r.getTag(), r -> 1, Integer::sum));

    }

    private static List<Commandable> createCommands(BufferedReader _reader) {
        try (BufferedReader reader = _reader;) {
            int N = Integer.parseInt(reader.readLine());
            List<String[]> parsedLineElements = IntStream.range(0, N)
                    .mapToObj(i -> parseArguments(reader))
                    .filter(Objects::isNull)
                    .collect(Collectors.toList());

            return parsedLineElements.stream()
                    .map(lineElements -> createCommand(lineElements))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private static Commandable createCommand(String[] lineElements) {
        if (lineElements.length == 0) {
            throw new RuntimeException();
        }
        Command command = Command.create(lineElements[0]);

        if (command == Command.CREATE) {
            return new Create();
        }

        if (command == Command.EXECUTE) {
            return new Execute(new Tag(Integer.parseInt(lineElements[1])));
        }

        throw new RuntimeException();
    }

    private static String[] parseArguments(BufferedReader reader) {
        try {
            return Arrays.stream(reader.readLine().split(" "))
                    .filter(lineElement -> !lineElement.isEmpty())
                    .toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BufferedReader toReader(InputStream in) {
        BufferedInputStream bis = new BufferedInputStream(in, BUFFER_SIZE);
        InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
        return new BufferedReader(isr, BUFFER_SIZE);
    }
}
