package chess.domain.position;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Position {
    public static final Map<String, Position> POOL = RowPosition.POOL
            .values()
            .stream()
            .flatMap(row -> ColumnPosition.POOL
                    .values()
                    .stream()
                    .map(column -> new Position(row, column)))
            .collect(toMap(position -> Position.toKey(position.rowPosition, position.columnPosition), position -> position));

    private final RowPosition rowPosition;
    private final ColumnPosition columnPosition;

    public Position(RowPosition rowPosition, ColumnPosition columnPosition) {
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
    }

    public static Position of(int rowPosition, int colPosition) {
        RowPosition row = RowPosition.from(rowPosition);
        ColumnPosition col = ColumnPosition.from(colPosition);
        return POOL.get(toKey(row, col));
    }

    public static String toKey(RowPosition rowPosition, ColumnPosition colPosition) {
        return String.valueOf(rowPosition.getRowNumber()) + colPosition.getColumnNumber();
    }

    public boolean isStraightWith(Position target) {
        return rowPosition.equals(target.rowPosition) || columnPosition.equals(target.columnPosition);
    }

    public boolean isDiagonalWith(Position target) {
        int rowInterval = rowPosition.intervalWith(target.rowPosition);
        int colInterval = columnPosition.intervalWith(target.columnPosition);
        return rowInterval == colInterval;
    }

    public int squaredDistanceWith(Position target) {
        int rowInterval = rowPosition.intervalWith(target.rowPosition);
        int colInterval = columnPosition.intervalWith(target.columnPosition);
        return (int) Math.pow(rowInterval, 2) + (int) Math.pow(colInterval, 2);
    }

    public boolean isSameRowWith(RowPosition rowPosition) {
        return this.rowPosition.equals(rowPosition);
    }

    public List<Position> findPath(Position target) {
        try {
            Direction direction = DirectionJudge.judge(this, target);
            return Stream.iterate(moveDirectionTo(direction),
                    p -> !target.equals(p),
                    p -> p.moveDirectionTo(direction)).toList();
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    public boolean isUpPosition(Position target) {
        return rowPosition.isLowerThan(target.rowPosition);
    }

    public boolean isDownPosition(Position target) {
        return rowPosition.isHigherThan(target.rowPosition);
    }

    public boolean isLeftPosition(Position target) {
        return columnPosition.isLeft(target.columnPosition);
    }

    public boolean isRightPosition(Position target) {
        return columnPosition.isRight(target.columnPosition);
    }

    private Position moveDirectionTo(Direction direction) {
        int nextRowStep = direction.getRowWeight();
        int nextColumnStep = direction.getColumnWeight();
        return movePosition(nextRowStep, nextColumnStep);
    }

    private Position movePosition(int rowInterval, int columnInterval) {
        return Position.of(rowPosition.findRowIntervalWith(rowInterval)
                , columnPosition.findColumnIntervalWith(columnInterval));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return Objects.equals(rowPosition, position.rowPosition) && Objects.equals(columnPosition,
                position.columnPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowPosition, columnPosition);
    }

    @Override
    public String toString() {
        return "[Position : "
                + rowPosition.toString()
                + columnPosition.toString()
                + "]";
    }

    public ColumnPosition getColumnPosition() {
        return columnPosition;
    }

    public RowPosition getRowPosition() {
        return rowPosition;
    }
}
