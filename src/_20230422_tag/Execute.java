package _20230422_tag;

import java.util.Queue;
import java.util.Set;

public class Execute implements Commandable {
    private final int tag;

    public Execute(int tag) {
        this.tag = tag;
    }

    @Override
    public ResultDto run(Queue<Integer> tags, Set<Integer> usableTags) {
        if (!usableTags.contains(tag)) {
            return new ResultDto(tag, false);
        }
        usableTags.remove(tag);
        tags.add(tag);
        return new ResultDto(tag, true);
    }
}
