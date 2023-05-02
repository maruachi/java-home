package _20230430_tag_stream;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tag implements Comparable {
    private final int value;

    public Tag(int value) {
        this.value = value;
    }

    public static List<Tag> createDefaultTags() {
        return Stream.of(
                new Tag(1),
                new Tag(2),
                new Tag(3),
                new Tag(4),
                new Tag(5),
                new Tag(6),
                new Tag(7),
                new Tag(8),
                new Tag(9))
                .collect(Collectors.toList());
    }

    public static Tag createFailTag() {
        return new Tag(-1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return value == tag.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public int compareTo(Object another) {
        if (another == null) {
            return -1;
        }

        if (!(another instanceof Tag)) {
            throw new RuntimeException();
        }

        return this.value - ((Tag) another).value;
    }
}
