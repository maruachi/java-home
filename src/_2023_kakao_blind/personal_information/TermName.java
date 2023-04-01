package _2023_kakao_blind.personal_information;

public class TermName {
    private final String value;

    public TermName(String value) {
        if (value == null) {
            throw new RuntimeException();
        }
        if (value.trim().length() != 1) {
            throw new RuntimeException();
        }

        this.value = value.trim();
    }
}
