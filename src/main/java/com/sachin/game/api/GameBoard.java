package com.sachin.game.api;

import com.sachin.game.api.core.GameConfiguration;

/**
 * Created by C5203803 on 7/11/2014.
 */
public interface GameBoard {

    void buildGameBoard(GameConfiguration configuration);

    void displayBoard();
}
