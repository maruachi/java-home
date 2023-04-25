package _20230425_tag;

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
}
