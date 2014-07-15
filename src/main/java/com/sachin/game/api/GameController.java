package com.sachin.game.api;

import com.sachin.game.api.beans.GameConfiguration;

import java.util.List;

/**
 * Created by SachinBhosale on 7/11/2014.
 */
public interface GameController {

    GameConfiguration getGameConfiguration ();

    void displayBoard();

    List<Player> getPlayers();

    int rollDice();

    GameRule getGameRule();

    void resetPlayers();
}
