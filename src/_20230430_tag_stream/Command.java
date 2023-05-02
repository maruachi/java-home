package _20230430_tag_stream;

import java.util.Queue;
import java.util.Set;

public interface Command {
    public ResultDto run(Queue<Tag> tags, Set<Tag> usableTags);
}
