package com.sachin.game.api;

import java.util.List;

/**
 * Created by SachinBhosale on 7/11/2014.
 */
public interface GameController {

    GameBoard getGameBoard();

    void displayBoard();

    List<Player> getPlayers();

    int rollDice();

    void resetPlayers();

    int getNextMove(int currentCell, int diceValue);
}
