package com.sachin.game.api.core;

/**
 * Created by C5203803 on 7/11/2014.
 */
public class Ladder {
    private final Cell fromCell;
    private final Cell toCell;

    public Ladder(int from, int to){
        fromCell = new Cell(from);
        toCell = new Cell(to);
    }

    public Cell getFromCell() {
        return fromCell;
    }

    public Cell getToCell() {
        return toCell;
    }
}
