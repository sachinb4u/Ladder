package com.sachin.game.api.beans;

/**
 * Created by SachinBhosale on 7/11/2014.
 *
 * GameMove holds information of player's game move.
 *
 */
public class GameMove {

    private Cell from;
    private Cell to;
    private int diceValue;

    public GameMove(int fromCell, int toCell, int diceValueIn){
        if(diceValueIn < 0 || diceValueIn > 6){
            throw new IllegalArgumentException("Dice value should be in between 1 and 6");
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameMove gameMove = (GameMove) o;

        if (diceValue != gameMove.diceValue) return false;
        if (!from.equals(gameMove.from)) return false;
        if (!to.equals(gameMove.to)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + diceValue;
        return result;
    }

    @Override
    public String toString() {
        return "\nMove{" +
                 from.getNumber() +
                ", " + to.getNumber() +
                ", dice=" + diceValue +
                "}";
    }
}
