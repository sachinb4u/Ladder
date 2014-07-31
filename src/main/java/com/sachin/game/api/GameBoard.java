package com.sachin.game.api;

import java.util.Map;

/**
 * Created by C5203803 on 7/31/2014.
 */
public interface GameBoard {
    int getRows();

    int getColumns();

    int getNoOfPlayers();

    int getMaxCell();

    Map<Integer, Integer> getLadders();

    Map<Integer, Integer> getSnakes();

    int getLadderForCell(int cell);

    int getSnakeForCell(int cell);

    boolean isSnakeBite(int cell);

    boolean isLadderJump(int cell);
}
