package com.sachin.game.api;

import com.sachin.game.api.impl.Cell;
import com.sachin.game.api.impl.GameMove;

import java.util.List;

/**
 * Created by C5203803 on 7/11/2014.
 */
public interface Player {

    String getName();

    void setName(String name);

    Cell playMove(int diceValue);

    Cell getCurrentPosition();

    void undoMove();

    List<GameMove> getMoveHistory();

    boolean isPlayerWon();

}
