package com.sachin.game.api;

import com.sachin.game.api.impl.GameConfiguration;

import java.util.List;

/**
 * Created by C5203803 on 7/11/2014.
 */
public interface GameController {

    GameConfiguration getGameConfiguration ();

    void displayBoard();

    List<Player> getPlayers();

    int rollDice();

    GameRule getGameRule();

    void resetPlayers();
}
