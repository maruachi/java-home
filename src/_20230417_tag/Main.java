package _20230417_tag;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Queue<Integer> usableTags = new PriorityQueue<>();
        for (int tag = 1; tag <= 9; tag++) {
            usableTags.add(tag);
        }
        Integer createFailCount = 0;

        HashSet<Integer> tasks = new HashSet<>();
        HashMap<Integer, Integer> taskFailCount = new HashMap<>();

        create(usableTags, tasks, createFailCount);
        create(usableTags, tasks, createFailCount);
        create(usableTags, tasks, createFailCount);
        create(usableTags, tasks, createFailCount);
        execute(usableTags, tasks, taskFailCount, 11);
        create(usableTags, tasks, createFailCount);
        create(usableTags, tasks, createFailCount);
        create(usableTags, tasks, createFailCount);
        create(usableTags, tasks, createFailCount);
        create(usableTags, tasks, createFailCount);
        create(usableTags, tasks, createFailCount);
        execute(usableTags, tasks, taskFailCount, 2);
        create(usableTags, tasks, createFailCount);
        execute(usableTags, tasks, taskFailCount, 2);
        execute(usableTags, tasks, taskFailCount, 11);
        execute(usableTags, tasks, taskFailCount, 2);
        execute(usableTags, tasks, taskFailCount, 5);
        execute(usableTags, tasks, taskFailCount, 5);
        execute(usableTags, tasks, taskFailCount, 2);
        execute(usableTags, tasks, taskFailCount, 5);
        execute(usableTags, tasks, taskFailCount, 5);

        printResult(usableTags, createFailCount, taskFailCount);
    }

    private static void printResult(Queue<Integer> usableTags, Integer createFailCount, HashMap<Integer, Integer> taskFailCount) {
        System.out.println("사용가능한 태그: " + usableTags);
        System.out.println("task 생성실패: " + createFailCount);
        System.out.println("task 수행 실패한 태그: " + taskFailCount);
    }

    private static void execute(Queue<Integer> usableTags, HashSet<Integer> tasks, Map<Integer, Integer> taskFailCount, int task) {
        if (!tasks.contains(task)) {
            if (!taskFailCount.containsKey(task)) {
                taskFailCount.put(task, 0);
            }

            taskFailCount.put(task, taskFailCount.get(task) + 1);
            return;
        }

        tasks.remove(task);
        usableTags.add(task);
    }

    private static void create(Queue<Integer> usableTags, Set<Integer> tasks, Integer createFailCount) {
        if (usableTags.isEmpty()) {
            //createFailCount는 Integer 객체로 immutable이라 아래 코드 수정 필요!
            createFailCount = new Integer(createFailCount + 1);
            return;
        }
        tasks.add(usableTags.poll());
    }
}
