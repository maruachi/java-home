package _20230422_tag;

import java.util.Queue;
import java.util.Set;

public interface Commandable {
    public ResultDto run(Queue<Integer> tags, Set<Integer> usableTags);

}
