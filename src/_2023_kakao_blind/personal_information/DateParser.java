package _2023_kakao_blind.personal_information;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateParser {
    private final DateTimeFormatter dateTimeFormatter;

    public DateParser(DateTimeFormatter dateTimeFormatter) {
        if (dateTimeFormatter == null) {
            throw new RuntimeException();
        }
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public LocalDate parse(String dateLine) {
        return LocalDate.parse(dateLine, dateTimeFormatter);
    }
}
