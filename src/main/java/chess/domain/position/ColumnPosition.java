package chess.domain.position;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class ColumnPosition {
    public static final int MIN_NUMBER = 0;
    public static final int MAX_NUMBER = 7;

    public static final LinkedHashMap<Integer, ColumnPosition> POOL = IntStream.rangeClosed(MIN_NUMBER, MAX_NUMBER)
            .boxed()
            .collect(toMap(i -> i, ColumnPosition::new, (v1, v2) -> v2, LinkedHashMap::new));


    private final int columnNumber;

    private ColumnPosition(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public static ColumnPosition from(int columnNumber) {
        return POOL.get(columnNumber);
    }

    public int findColumnIntervalWith(int columnInterval) {
        return columnNumber + columnInterval;
    }

    public int intervalWith(ColumnPosition otherColumnPosition) {
        return Math.abs(columnNumber - otherColumnPosition.columnNumber);
    }

    public boolean isLeft(ColumnPosition otherColumnPosition) {
        return columnNumber < otherColumnPosition.columnNumber;
    }

    public boolean isRight(ColumnPosition otherColumnPosition) {
        return columnNumber > otherColumnPosition.columnNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ColumnPosition that = (ColumnPosition) o;
        return columnNumber == that.columnNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnNumber);
    }

    @Override
    public String toString() {
        return "COLUMN : " + columnNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}
