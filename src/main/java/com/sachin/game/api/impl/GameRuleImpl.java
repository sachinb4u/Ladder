package com.sachin.game.api.impl;

import com.sachin.game.api.GameRule;
import com.sachin.game.api.beans.Cell;
import com.sachin.game.api.beans.GameConfiguration;
import com.sachin.game.api.beans.Ladder;
import com.sachin.game.api.beans.Snake;

/**
 * Created by SachinBhosale on 7/13/2014.
 */
public class GameRuleImpl implements GameRule {

    private final GameConfiguration configuration;

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
        if(diceValue < 1 || diceValue > 6){
            throw new IllegalArgumentException("DiceValue cannot be greater than 6 and less than 1");
        }

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

        /**
         * for winning exact number is required.
         */
        if(nextCell.getNumber() > configuration.getMaxCell()){
            return currentCell;
        }

        return nextCell;
    }
}
