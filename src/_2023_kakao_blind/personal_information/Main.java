package _2023_kakao_blind.personal_information;

//https://school.programmers.co.kr/learn/courses/30/lessons/150370

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String today = "2022.05.19";
        String[] terms = new String[]{"A 6", "B 12", "C 3"};
        String[] privacies = new String[]{
                "2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"
        };

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        DateParser dateParser = new DateParser(dateTimeFormatter);

        LocalDate todayDate = dateParser.parse(today);

        HashMap<Character, Integer> termMapper = new HashMap<>();
        for (String termLine : terms) {
            String[] termLineElement = termLine.split(" ");
            char term = termLineElement[0].charAt(0);
            int months = Integer.parseInt(termLineElement[1]);
            termMapper.put(term, months);
        }

        int num = 1;
        List<Integer> destroyNum = new ArrayList<>();
        for (String privacy : privacies) {
            String[] privacyElement = privacy.split(" ");
            LocalDate startDate = dateParser.parse(privacyElement[0]);
            char term = privacyElement[1].charAt(0);

            int month = termMapper.get(term);
            LocalDate dueDate = startDate.plusMonths(month).minusDays(1);
            if (todayDate.isAfter(dueDate)) {
                destroyNum.add(num);
            }
            num++;
        }

        System.out.println(destroyNum);
    }
}
