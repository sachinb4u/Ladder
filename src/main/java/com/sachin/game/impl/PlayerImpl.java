package com.sachin.game.impl;

import com.sachin.game.api.GameController;
import com.sachin.game.api.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by SachinBhosale on 7/12/2014.
 */
public class PlayerImpl implements Player {

    private final GameController controller;
    private final Stack<String> lastMoves = new Stack<String>();
    private int currentPosition;
    private String name;

    public PlayerImpl(GameController gameController) {
        controller = gameController;
        this.name = "Player1";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int playMove(int diceValue) {
        int nextCell = controller.getNextMove(getCurrentPosition(), diceValue);

        StringBuilder builder = new StringBuilder("f(").append(getCurrentPosition()).append(",").append(diceValue).append(")=").append(nextCell);
        lastMoves.add(builder.toString());
//        System.out.println(builder.toString());
        currentPosition = nextCell;
        return nextCell;
    }

    @Override
    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public void undoMove() {

        String lastMove = lastMoves.pop();

        String[] strArr = lastMove.split("=");
        String lastPosition = strArr[0].split(",")[0].substring(2);
        currentPosition = Integer.parseInt(lastPosition);
    }

    @Override
    public List<String> getMoveHistory() {

        List<String> history = new ArrayList<String>();

        for (String gameMove : lastMoves) {
            history.add(gameMove);
        }

        return history;
    }

    @Override
    public boolean isPlayerWon() {

        return getCurrentPosition() == controller.getGameBoard().getMaxCell() ? true : false;
    }

}
