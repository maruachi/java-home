package _2023_kakao_blind.personal_information;

//https://school.programmers.co.kr/learn/courses/30/lessons/150370

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        String today = "2022.05.19";
        String[] termDurations = new String[]{"A 6", "B 12", "C 3"};
        String[] privacies = new String[]{
                "2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"
        };

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        DateParser dateParser = new DateParser(dateTimeFormatter);

        LocalDate todayDate = dateParser.parse(today);

        int newTermId = 1;
        List<Term> terms = new ArrayList<>();
        for (String privacy : privacies) {
            String[] privacyElement = privacy.split(" ");

            LocalDate startDate = dateParser.parse(privacyElement[0]);
            TermName termName = new TermName(privacyElement[1]);
            Term term = new Term(newTermId, termName, startDate);
            newTermId++;

            terms.add(term);
        }

        HashMap<TermName, Integer> termMonthDuration = new HashMap<>();
        for (String termLine : termDurations) {
            String[] termLineElement = termLine.split(" ");

            TermName termName = new TermName(termLineElement[0]);
            int monthDuration = Integer.parseInt(termLineElement[1]);

            termMonthDuration.put(termName, monthDuration);
        }
        TermDueDeterminer termDueDeterminer = new TermDueDeterminer(termMonthDuration, todayDate);

        List<Integer> dueTermIds = new ArrayList<>();
        for (Term term : terms) {
            if (termDueDeterminer.isDue(term)) {
                dueTermIds.add(term.getId());
            }
        }

        System.out.println(dueTermIds);
    }

    public static class DateParser {
        private final DateTimeFormatter dateTimeFormatter;

        public DateParser(DateTimeFormatter dateTimeFormatter) {
            this.dateTimeFormatter = dateTimeFormatter;
        }

        public LocalDate parse(String dateLine) {
            return LocalDate.parse(dateLine, dateTimeFormatter);
        }
    }

}
