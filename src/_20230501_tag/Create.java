package _20230501_tag;

import java.util.Queue;
import java.util.Set;

public class Create implements Commandable {

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
