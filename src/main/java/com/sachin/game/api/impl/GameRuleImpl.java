package com.sachin.game.api.impl;

import com.sachin.game.api.GameRule;

/**
 * Created by C5203803 on 7/13/2014.
 */
public class GameRuleImpl implements GameRule {

    private GameConfiguration configuration;

    public GameRuleImpl(GameConfiguration configuration){
        this.configuration = configuration;
    }

    @Override
    public boolean isSnakeBite(Cell cell) {
        Snake snake = configuration.getSnakeForCell(cell);

        return snake == null ? false : true;
    }

    @Override
    public boolean isLadderJump(Cell cell) {
        Ladder ladder = configuration.getLadderForCell(cell);

        return ladder == null ? false : true;
    }

    @Override
    public Cell getNextMove(Cell currentCell, int diceValue){
        int nextCellNumber = currentCell.getNumber() + diceValue;

        Cell nextCell = new Cell(nextCellNumber);

        if(isSnakeBite(nextCell)){
            Snake snake = configuration.getSnakeForCell(nextCell);

            nextCell = snake.getToCell();
        }

        if(isLadderJump(nextCell)){
            Ladder ladder = configuration.getLadderForCell(nextCell);

            nextCell = ladder.getToCell();
        }

        return nextCell;
    }
}
