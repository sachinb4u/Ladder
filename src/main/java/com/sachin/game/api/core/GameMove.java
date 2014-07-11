package com.sachin.game.api.core;

/**
 * Created by C5203803 on 7/11/2014.
 */
public class GameMove {

    private Cell from;
    private Cell to;
    private int diceValue;

    public GameMove(int fromCell, int toCell, int diceValueIn){
        from = new Cell(fromCell);
        to = new Cell(toCell);
        diceValue = diceValueIn;
    }

    public Cell getFrom() {
        return from;
    }

    public Cell getTo() {
        return to;
    }

    public int getDiceValue() {
        return diceValue;
    }
}
