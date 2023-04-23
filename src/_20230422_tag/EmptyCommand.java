package _20230422_tag;

import java.util.Queue;
import java.util.Set;

public class EmptyCommand implements Commandable {

    @Override
    public ResultDto run(Queue<Integer> tags, Set<Integer> usableTags) {
        return null;
    }
}
