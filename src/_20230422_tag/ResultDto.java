package _20230422_tag;

public class ResultDto {
    private final int tag;
    private final boolean success;

    public ResultDto(int tag, boolean success) {
        this.tag = tag;
        this.success = success;
    }

    public int getTag() {
        return tag;
    }

    public boolean isSuccess() {
        return success;
    }
}
