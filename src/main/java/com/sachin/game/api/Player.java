package com.sachin.game.api;


import java.util.List;

/**
 * Created by SachinBhosale on 7/11/2014.
 */
public interface Player {

    String getName();

    void setName(String name);

    int playMove(int diceValue);

    int getCurrentPosition();

    void undoMove();

    List<String> getMoveHistory();

    boolean isPlayerWon();

}
