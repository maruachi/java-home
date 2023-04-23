package _20230422_tag;

import java.util.Queue;
import java.util.Set;

public class Create implements Commandable{

    public static final int FAIL = -1;

    @Override
    public ResultDto run(Queue<Integer> tags, Set<Integer> usableTags) {
        if (tags.isEmpty()) {
            return new ResultDto(FAIL, false);
        }

        int tag = tags.poll();
        usableTags.add(tag);
        return new ResultDto(tag, true);
    }
}
