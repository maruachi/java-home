package _20230430_tag_stream;

public class ResultDto {
    private final Tag tag;
    private final boolean success;

    public ResultDto(Tag tag, boolean success) {
        this.tag = tag;
        this.success = success;
    }

    public Tag getTag() {
        return tag;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFail() {
        return !isSuccess();
    }
}
