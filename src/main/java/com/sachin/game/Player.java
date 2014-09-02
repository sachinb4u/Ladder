package com.sachin.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by SachinBhosale on 7/12/2014.
 */
public class Player {

    private final GameController controller;
    private int currentPosition;
    private String name;

    public Player(GameController gameController) {
        controller = gameController;
        this.name = "Player1";
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int playMove(int diceValue) {

        assert (diceValue > 0 && diceValue < 7) : "Invalid DiceValue";

        int nextCell = controller.getNextMove(getCurrentPosition(), diceValue);

        StringBuilder builder = new StringBuilder("f(").append(getCurrentPosition()).append(",").append(diceValue).append(")=").append(nextCell);
        currentPosition = nextCell;
        return nextCell;
    }


    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isPlayerWon() {

        return getCurrentPosition() == controller.getGameBoard().getMaxCell() ? true : false;
    }

}
