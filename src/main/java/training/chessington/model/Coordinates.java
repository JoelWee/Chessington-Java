package training.chessington.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Coordinates {
    private final int row;
    private final int col;
    public static List<Coordinates> axialDirections;
    public static List<Coordinates> diagonalDirections;

    static {
        axialDirections = Stream.of(new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}})
                .map(dir -> new Coordinates(dir[0], dir[1]))
                .collect(Collectors.toList());

        diagonalDirections = Stream.of(new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}})
                .map(dir -> new Coordinates(dir[0], dir[1]))
                .collect(Collectors.toList());
    }

    public static int manhattanDistanceBetween(Coordinates a, Coordinates b) {
        int rowDist = Math.abs(a.getRow() - b.getRow());
        int colDist =  Math.abs(a.getCol() - b.getCol());
        return rowDist + colDist;
    }

    public Coordinates(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return row == that.row &&
                col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return String.format("row %d, column %d", row, col);
    }

    public Coordinates plus(int rowDiff, int colDiff) {
        return new Coordinates(row + rowDiff, col + colDiff);
    }

    public Coordinates plus(Coordinates toAdd) {
        return new Coordinates(row + toAdd.getRow(), col + toAdd.getCol());
    }

    public boolean isOnBoard() {
        return 0 <= row && row < 8 && 0 <= col && col < 8;
    }
}
