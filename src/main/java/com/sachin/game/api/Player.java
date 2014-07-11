package com.sachin.game.api;

import com.sachin.game.api.core.Cell;
import com.sachin.game.api.core.GameMove;

import java.util.List;

/**
 * Created by C5203803 on 7/11/2014.
 */
public interface Player {

    int rollDice();

    Cell playMove(int diceValue);

    Cell getCurrentPosition();

    void undoMove();

    List<GameMove> getMoveHistory();

}
