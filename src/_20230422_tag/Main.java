package _20230422_tag;


import javax.swing.text.html.parser.Entity;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static final int FAIL_TAG = -1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = Integer.parseInt(scanner.nextLine());

        // 데이터 입력 및 변환 컨텍스트
        Set<Command> commandSet = new HashSet<>(Arrays.asList(Command.CREATE, Command.EXECUTE));

        Queue<Commandable> commands = new ArrayDeque<>();
        for (int i = 0; i < N; i++) {
            String line = scanner.nextLine();
            String[] lineElements = Arrays.stream(line.split(" "))
                    .filter((element) -> !element.isEmpty())
                    .toArray(String[]::new);
            Command command = Command.create(lineElements[0]);

            if (!commandSet.contains(command)) {
                throw new RuntimeException();
            }

            Commandable commandable = new EmptyCommand();
            if (Command.CREATE == command) {
                commandable = new Create();
            }

            if (Command.EXECUTE == command) {
                int tag = Integer.parseInt(lineElements[1]);
                commandable = new Execute(tag);
            }
            commands.add(commandable);
        }

        System.out.println(commands);

        // 처리 컨텍스트
        Queue<Integer> tags = new PriorityQueue<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        Set<Integer> usableTags = new HashSet<>();

        HashMap<Integer, Integer> failHistory = new HashMap<>();
        while (!commands.isEmpty()) {
            Commandable command = commands.poll();

            ResultDto resultDto = command.run(tags, usableTags);

            // 실패처리 추상화
            if (!resultDto.isSuccess()) {
                int tag = resultDto.getTag();
                int count = failHistory.getOrDefault(tag, 0);

                failHistory.put(tag, count + 1);
            }
        }

        // 출력 컨텍스트
        StringBuilder builder = new StringBuilder(30);
        while (!tags.isEmpty()) {
            builder.append(" " + tags.poll());
        }
        System.out.print("대기중인태그:");
        System.out.println(builder);

        System.out.println("생성실패횟수:" + " " + failHistory.getOrDefault(FAIL_TAG, 0));
        List<Map.Entry<Integer, Integer>> sortedFailHistory = failHistory.entrySet().stream()
                .filter(entry -> entry.getKey() != FAIL_TAG)
                .sorted(new Comparator<Map.Entry<Integer, Integer>>() {
                    @Override
                    public int compare(Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) {
                        int compareTo = e2.getValue().compareTo(e1.getValue());
                        if (compareTo == 0) {
                            return e1.getKey().compareTo(e2.getValue());
                        }
                        return compareTo;
                    }
                })
                .collect(Collectors.toList());

        System.out.print("실생실패이력:");
        for (Map.Entry<Integer, Integer> entry : sortedFailHistory) {
            System.out.print(MessageFormat.format( " " + "{0}({1})", entry.getKey(), entry.getValue()));
        }
        System.out.println();
    }
}
