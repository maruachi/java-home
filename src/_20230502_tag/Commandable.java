package _20230502_tag;

import java.util.Queue;
import java.util.Set;

public interface Commandable {
    public ResultDto run(Queue<Tag> tags, Set<Tag> usableTags);

}
