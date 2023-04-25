package _20230425_tag;

import java.util.Queue;
import java.util.Set;

public class Execute implements Command {
    private final Tag tag;

    public Execute(Tag tag) {
        this.tag = tag;
    }

    @Override
    public ResultDto run(Queue<Tag> tags, Set<Tag> usableTags) {
        if (!usableTags.contains(tag)) {
            return new ResultDto(tag, false);
        }

        tags.add(tag);
        usableTags.remove(tag);
        return new ResultDto(tag, true);
    }
}
