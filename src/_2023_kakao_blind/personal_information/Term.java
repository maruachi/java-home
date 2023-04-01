package _2023_kakao_blind.personal_information;

import java.time.LocalDate;

public class Term {
    private final int id;
    private final TermName termName;
    private final LocalDate startDate;

    public Term(int id, TermName termName, LocalDate startDate) {
        this.id = id;
        this.termName = termName;
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public TermName getTerm() {
        return termName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}

