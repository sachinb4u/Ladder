package com.sachin.game.api;

import com.sachin.game.api.core.Cell;

/**
 * Created by C5203803 on 7/11/2014.
 */
public interface GameRules {

    boolean isSnakeBite(Cell cell);

    boolean isLadderJump(Cell cell);

    boolean isPlayerWon(Cell cell);
}
