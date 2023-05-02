package _20230502_tag;

public class ResultDto {
    private final Tag tag;
    private final boolean isSuccess;

    public ResultDto(Tag tag, boolean isSuccess) {
        this.tag = tag;
        this.isSuccess = isSuccess;
    }

    public Tag getTag() {
        return tag;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isFail() {
        return !isSuccess();
    }
}
