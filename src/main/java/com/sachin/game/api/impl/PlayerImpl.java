package com.sachin.game.api.impl;

import com.sachin.game.api.GameController;
import com.sachin.game.api.Player;
import com.sachin.game.api.beans.Cell;
import com.sachin.game.api.beans.GameMove;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by C5203803 on 7/12/2014.
 */
public class PlayerImpl implements Player {

    private final GameController controller;

    private Cell currentPosition = new Cell(0);

    private final Stack<GameMove> lastMoves = new Stack<GameMove>();

    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public PlayerImpl(GameController gameController){
        controller = gameController;
    }

    @Override
    public Cell playMove(int diceValue) {
        Cell nextCell = controller.getGameRule().getNextMove(currentPosition, diceValue);

        GameMove gameMove = new GameMove(currentPosition.getNumber(), nextCell.getNumber(),diceValue);
        lastMoves.add(gameMove);

        currentPosition = nextCell;
        return nextCell;
    }

    @Override
    public Cell getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public void undoMove() {
        GameMove lastMove = lastMoves.pop();

        currentPosition = lastMove.getFrom();
    }

    @Override
    public List<GameMove> getMoveHistory() {

        List<GameMove> history = new ArrayList<GameMove>();

        for(GameMove gameMove : lastMoves){
            history.add(gameMove);
        }

        return  history;
    }

    @Override
    public boolean isPlayerWon() {

        return currentPosition.getNumber() >= controller.getGameConfiguration().getMaxCell() ? true : false;
    }

}
