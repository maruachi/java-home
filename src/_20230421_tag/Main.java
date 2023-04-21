package _20230421_tag;

import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // 데이터의 개수 첫줄로 받기
        Scanner scanner = new Scanner(System.in);
        int N = Integer.parseInt(scanner.nextLine());

        // 데이터 입력 받고 변환하기
        Set<Command> commandSet = new HashSet<>();
        commandSet.add(Command.CREATE);
        commandSet.add(Command.EXECUTE);

        Queue<CommandDto> commandDtos = new ArrayDeque<>();
        Queue<Integer> arguments = new ArrayDeque<>();
        for (int i = 0; i < N; i++) {
            String line = scanner.nextLine();
            String[] lineElements = Arrays.stream(line.split(" "))
                    .filter((element) -> !element.isEmpty())
                    .toArray(String[]::new);
            Command command = Command.create(lineElements[0]);

            if (!commandSet.contains(command)) {
                throw new RuntimeException();
            }

            switch (command) {
                case CREATE:
                    commandDtos.add(new CommandDto(Command.CREATE, 0));
                    break;
                case EXECUTE:
                    commandDtos.add(new CommandDto(Command.EXECUTE, 1));

                    int tag = Integer.parseInt(lineElements[1]);
                    arguments.add(tag);
                    break;
            }
        }

        //데이터 처리하기
        Queue<Integer> tags = new PriorityQueue<>();
        tags.add(1);
        tags.add(2);
        tags.add(3);
        tags.add(4);
        tags.add(5);
        tags.add(6);
        tags.add(7);
        tags.add(8);
        tags.add(9);
        Set<Integer> usableTags = new HashSet<>();

        HashMap<Integer, Integer> failHistory = new HashMap<>();
        for (CommandDto commandDto : commandDtos) {
            //명령어 처리
            ResultDto resultDto = null;
            switch (commandDto.getCommand()) {
                case CREATE:
                    resultDto = create(tags, usableTags);
                    break;
                case EXECUTE:
                    int tag = arguments.poll();
                    resultDto = execute(tags, usableTags, tag);
                    break;
            }

            //실패 이력 처리
            if (!resultDto.isSuccess()) {
                int tag = resultDto.getTag();
                int count = failHistory.getOrDefault(tag, 0);
                failHistory.put(tag, count);
            }
        }


    }

    private static ResultDto execute(Queue<Integer> tags, Set<Integer> usableTags, int tag) {
        if (!usableTags.contains(tag)) {
            return new ResultDto(tag, false);
        }
        usableTags.remove(tag);
        tags.add(tag);
        return new ResultDto(tag, true);
    }

    private static ResultDto create(Queue<Integer> tags, Set<Integer> usableTags) {
        if (tags.isEmpty()) {
            return new ResultDto(-1, false);
        }
        usableTags.add(tags.peek());
        return new ResultDto(tags.poll(), true);
    }
}
