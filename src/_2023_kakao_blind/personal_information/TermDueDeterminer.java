package _2023_kakao_blind.personal_information;

import java.time.LocalDate;
import java.util.HashMap;

public class TermDueDeterminer {
    private final HashMap<TermName, Integer> termMonthDuration;
    private final LocalDate todayDate;

    public TermDueDeterminer(HashMap<TermName, Integer> termMonthDuration, LocalDate todayDate) {
        this.termMonthDuration = termMonthDuration;
        this.todayDate = todayDate;
    }

    private LocalDate calculateDueDate(LocalDate startDate, Integer durationMonth) {
        return startDate.plusMonths(durationMonth).minusDays(1);
    }

    public boolean isDue(Term term) {
        LocalDate startDate = term.getStartDate();
        Integer monthDuration = termMonthDuration.get(term.getTerm());
        
        LocalDate dueDate = calculateDueDate(startDate, monthDuration);
        return todayDate.isAfter(dueDate);
    }

}
