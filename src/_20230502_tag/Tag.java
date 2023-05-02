package _20230502_tag;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Tag implements Comparable{
    private final int value;

    public Tag(int value) {
        this.value = value;
    }

    public static List<Tag> createDefaultTags() {
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9).stream()
                .map(value -> new Tag(value))
                .collect(Collectors.toList());
    }

    public static Tag createFailTag(){
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

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }

        if (!(o instanceof Tag)) {
            throw new RuntimeException();
        }

        return ((Tag) o).value - this.value;
    }
}
