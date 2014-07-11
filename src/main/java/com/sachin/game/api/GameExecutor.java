package com.sachin.game.api;

/**
 * Created by C5203803 on 7/11/2014.
 */
public interface GameExecutor {

    void setupGame(GameBoard gameBoard);

    void executeGame();

    void saveGame();

    void loadGame();
}
