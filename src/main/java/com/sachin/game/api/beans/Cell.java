package com.sachin.game.api.beans;

/**
 * Created by C5203803 on 7/11/2014.
 */
public final class Cell {

    private final int number;

    public Cell(int num){
        if(num < 0){
            throw new IllegalArgumentException("Cell cannot have negative number");
        }
        this.number = num;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (number != cell.number) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return number;
    }
}

