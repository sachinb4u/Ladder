package com.sachin.game.api;

import com.sachin.game.api.beans.Cell;

/**
 * Created by C5203803 on 7/11/2014.
 */
public interface GameRule {

    boolean isSnakeBite(Cell cell);

    boolean isLadderJump(Cell cell);

    Cell getNextMove(Cell currentCell, int diceValue);
}
