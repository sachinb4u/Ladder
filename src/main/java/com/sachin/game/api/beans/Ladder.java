package com.sachin.game.api.beans;

/**
 * Created by C5203803 on 7/11/2014.
 */
public final class Ladder {
    private final Cell fromCell;
    private final Cell toCell;

    public Ladder(int from, int to){
        if(from <= 0 || to <= 0 || to <= from){
            throw new IllegalArgumentException("Ladder cannot go to lesser or negative cell");
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

        Ladder ladder = (Ladder) o;

        if (!fromCell.equals(ladder.fromCell)) return false;
        if (!toCell.equals(ladder.toCell)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fromCell.hashCode();
        result = 31 * result + toCell.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ladder{" +
                "fromCell=" + fromCell +
                ", toCell=" + toCell +
                '}';
    }
}
