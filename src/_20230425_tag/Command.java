package _20230425_tag;

import java.util.Queue;
import java.util.Set;

public interface Command {
    public ResultDto run(Queue<Tag> tags, Set<Tag> usableTags);
}
