package _20230430_tag_stream;

import java.util.Queue;
import java.util.Set;

public class Create implements Command {

    @Override
    public ResultDto run(Queue<Tag> tags, Set<Tag> usableTags) {
        if (tags.isEmpty()) {
            return new ResultDto(Tag.createFailTag(), false);
        }
        Tag tag = tags.poll();
        usableTags.add(tag);
        return new ResultDto(tag, true);
    }
}
