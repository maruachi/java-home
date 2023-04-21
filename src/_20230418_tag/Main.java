package _20230418_tag;

import java.util.*;

// small step: dto를 써봐라, enum을 써봐라, 추상화에 대해서 분기처리해봐라

// 피드백 노션베이스
// 조금더 구체적할테지만, 모호한 부분이 이 수 있다.
// 그런부분 추가질문을 통해 조율하는 걸로

// 제가 일단 많이 -> 개인시간 투자 X
// 아침 + 시간 -> ??
// 빠르게 성장하신 분??? 특징??
//
public class Main {
    public static void main(String[] args) {
        PriorityQueue<Integer> tags = new PriorityQueue<>();
        for (int tag = 1; tag <= 9; tag++) {
            tags.add(tag);
        }

        Set<Integer> tasks = new HashSet<>();

        int createFailCount = 0;
        HashMap<Integer, Integer> taskFailHistory = new HashMap<>();

        Scanner scanner = new Scanner(System.in);
        int N = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < N; i++) {
            String line = scanner.nextLine();
            String[] lineElements = line.split("[ ]+");
            int numLineElement = lineElements.length;

            if (numLineElement == 1 && "quit".equals(lineElements[0])) {
                break;
            }

            if (numLineElement == 1 && "create".equals(lineElements[0])) {
                boolean isCreated = create(tags, tasks);
                if (!isCreated) {
                    createFailCount++;
                }
                continue;
            }

            if (numLineElement == 2 && "execute".equals(lineElements[0])) {
                int tag = Integer.parseInt(lineElements[1]);
                boolean isExecuted = execute(tags, tasks, tag);
                if (!isExecuted) {
                    updateTaskFailHistory(taskFailHistory, tag);
                }
                continue;
            }
        }

        System.out.print("사용가능한 TAG:");
        Iterator<Integer> tagIterator = tags.iterator();
        while (tagIterator.hasNext()) {
            System.out.print(" " + tagIterator.next());
        }
        System.out.println();
        System.out.println("TASK 생성 실패: " + createFailCount);

        List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(taskFailHistory.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> entry1, Map.Entry<Integer, Integer> entry2) {
                if (entry1.getValue().equals(entry2.getValue())) {
                    return entry1.getKey() - entry2.getKey();
                }
                return entry2.getValue() - entry1.getValue();
            }
        });

        System.out.println("TASK 수행 실패한 태그: " + entryList);
    }

    private static void updateTaskFailHistory(HashMap<Integer, Integer> taskFailHistory, int tag) {
        if (!taskFailHistory.containsKey(tag)) {
            taskFailHistory.put(tag, 0);
        }
        taskFailHistory.put(tag, taskFailHistory.get(tag) + 1);
    }

    private static boolean execute(PriorityQueue<Integer> tags, Set<Integer> tasks, int tag) {
        if (!tasks.contains(tag)) {
            return false;
        }

        tasks.remove(tag);
        tags.add(tag);
        return true;
    }

    private static boolean create(PriorityQueue<Integer> tags, Set<Integer> tasks) {
        if (tags.isEmpty()) {
            return false;
        }

        tasks.add(tags.poll());
        return true;
    }
}
