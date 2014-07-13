package com.sachin.game.api.beans;

/**
 * Created by C5203803 on 7/11/2014.
 */
public class Snake {
    private final Cell fromCell;
    private final Cell toCell;

    public Snake(int from, int to) {
        if (from <= 0 || to <= 0 || from <= to) {
            throw new IllegalArgumentException("Snake cannot take to greater or negative cell");
        }
        fromCell = new Cell(from);
        toCell = new Cell(to);
    }

    public Cell getFromCell() {
        return fromCell;
    }

    public Cell getToCell() {
        return toCell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Snake snake = (Snake) o;

        if (fromCell != null ? !fromCell.equals(snake.fromCell) : snake.fromCell != null) return false;
        if (toCell != null ? !toCell.equals(snake.toCell) : snake.toCell != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fromCell != null ? fromCell.hashCode() : 0;
        result = 31 * result + (toCell != null ? toCell.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Snake{" +
                "fromCell=" + fromCell +
                ", toCell=" + toCell +
                '}';
    }
}
