package chess.domain.position;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class RowPosition {
    public static final int MIN_NUMBER = 0;
    public static final int MAX_NUMBER = 7;
    public static final LinkedHashMap<Integer, RowPosition> POOL = IntStream.rangeClosed(MIN_NUMBER, MAX_NUMBER)
            .boxed()
            .collect(toMap(i -> i, RowPosition::new, (v1, v2) -> v2, LinkedHashMap::new));

    private final int rowNumber;

    private RowPosition(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public static RowPosition from(int rowNumber) {
        return POOL.get(rowNumber);
    }

    public int findRowIntervalWith(int rowInterval) {
        return rowNumber + rowInterval;
    }

    public RowPosition reverse() {
        return new RowPosition(MAX_NUMBER - rowNumber);
    }

    public int intervalWith(RowPosition otherRowPosition) {
        return Math.abs(rowNumber - otherRowPosition.rowNumber);
    }

    public boolean isHigherThan(RowPosition target) {
        return rowNumber > target.rowNumber;
    }

    public boolean isLowerThan(RowPosition target) {
        return rowNumber < target.rowNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RowPosition that = (RowPosition) o;
        return rowNumber == that.rowNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNumber);
    }

    @Override
    public String toString() {
        return "ROW : " + rowNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }
}
