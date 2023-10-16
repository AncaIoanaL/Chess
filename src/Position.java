import java.util.ArrayList;
import java.util.List;

public class Position {

    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean validPosition() {
        return row >= 0 && row < 8 && column >= 0 && column < 8;
    }

    public List<Position> getInBetweenPositions(Position otherPosition) {
        List<Position> positions = new ArrayList<>();

        // moving horizontally
        if (row == otherPosition.getRow()) {
            for (int i = 1; i < Math.max(column, otherPosition.getColumn()); i++) {
                positions.add(new Position(row, Math.min(column, otherPosition.getColumn()) + i));
            }
        // moving vertically
        } else if (column == otherPosition.getColumn()) {
            for (int i = 1; i < Math.max(row, otherPosition.getRow()); i++) {
                positions.add(new Position(Math.min(row, otherPosition.getRow()) + i, column));
            }
        // moving diagonally
        } else if (Math.abs(row - otherPosition.getRow()) == Math.abs(column - otherPosition.getColumn())) {
            int rowOffset;
            int columnOffset;

            if (row < otherPosition.getRow()) {
                rowOffset = 1;
            } else {
                rowOffset = -1;
            }

            if (column < otherPosition.getColumn()) {
                columnOffset = 1;
            } else {
                columnOffset = -1;
            }

            for (int i = 1; i < Math.abs(row - otherPosition.getRow()); i++) {
                positions.add(new Position(row + rowOffset * i, column + columnOffset * i));
            }
        }

        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (row != position.row) return false;
        return column == position.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
