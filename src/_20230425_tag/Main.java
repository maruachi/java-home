package _20230425_tag;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        //입력 및 변환 컨텍스트
        Scanner scanner = new Scanner(System.in);
        int N = Integer.parseInt(scanner.nextLine());

        List<Command> commands = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            String[] lineElements = Arrays.stream(scanner.nextLine().split(" "))
                    .filter(element -> !element.isEmpty())
                    .toArray(String[]::new);

            Command command = createCommand(lineElements);

            commands.add(command);
        }

        // 처리컨텍스트
        Queue<Tag> tags = new PriorityQueue<>(Tag.createDefaultTags());
        Set<Tag> usableTags = new TreeSet<>();
        Map<Tag, Integer> failCount = new HashMap<>();
        for (Command command : commands) {
            ResultDto resultDto = command.run(tags, usableTags);

            // 실패처리
            if (resultDto.isFail()) {
                Tag tag = resultDto.getTag();
                int count = failCount.getOrDefault(tag, 0);
                failCount.put(tag, count + 1);
            }
        }

        // 출력 컨텍스트
        StringBuilder builder = new StringBuilder(100);
        List<String> printLines = new ArrayList<>();

        builder.setLength(0);
        builder.append("대기중인 태그:");
        while (!tags.isEmpty()) {
            builder.append(' ');
            builder.append(tags.poll());
        }
        printLines.add(builder.toString());

        builder.setLength(0);
        builder.append("생성실패횟수:");
        builder.append(' ');
        builder.append(failCount.getOrDefault(Tag.createFailTag(), 0));
        printLines.add(builder.toString());

        builder.setLength(0);
        builder.append("태그별 실행실패횟수:");
        builder.append(' ');
        builder.append(
                failCount.entrySet().stream()
                        .filter(entry -> entry.getKey().equals(Tag.createFailTag()))
                        .sorted((entry1, entry2)->{
                            int compareTo = entry2.getValue().compareTo(entry1.getValue());
                            if (compareTo == 0) {
                                return entry1.getKey().compareTo(entry2.getKey());
                            }

                            return compareTo;
                        })
                        .map((entry)->entry.getKey().toString() + entry.getValue().toString())
                        .collect(Collectors.joining())
        );

        for (String line : printLines) {
            System.out.println(line);
        }
    }

    private static Command createCommand(String[] lineElements) {
        String commandStr = lineElements[0];

        if ("create".equals(commandStr)) {
            return new Create();
        }

        if ("execute".equals(commandStr)) {
            Tag tag = new Tag(Integer.parseInt(lineElements[1]));
            return new Execute(tag);
        }

        throw new RuntimeException();
    }
}
