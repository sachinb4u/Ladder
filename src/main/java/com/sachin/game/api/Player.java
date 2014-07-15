package com.sachin.game.api;

import com.sachin.game.api.beans.Cell;
import com.sachin.game.api.beans.GameMove;

import java.util.List;

/**
 * Created by SachinBhosale on 7/11/2014.
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
