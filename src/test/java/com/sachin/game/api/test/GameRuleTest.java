package com.sachin.game.api.test;

import com.sachin.game.api.GameRule;
import com.sachin.game.api.beans.Cell;
import com.sachin.game.api.beans.GameConfiguration;
import com.sachin.game.api.beans.Ladder;
import com.sachin.game.api.beans.Snake;
import com.sachin.game.api.impl.GameRuleImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by SachinBhosale on 7/24/2014.
 */
public class GameRuleTest {

    private GameRule gameRule;

    private GameConfiguration gameConfiguration;

    @Before
    public void setup() {
        gameConfiguration = initGameConfiguration();
        Assert.assertNotNull(gameConfiguration);

        gameRule = new GameRuleImpl(gameConfiguration);
        Assert.assertNotNull(gameRule);
    }

    private GameConfiguration initGameConfiguration() {

        GameConfiguration.GameConfigurationBuilder builder = new GameConfiguration.GameConfigurationBuilder();
        builder.setColumns(10);
        builder.setRows(10);
        builder.setNoOfPlayers(2);

        Ladder ladder1 = new Ladder(5, 20);
        Ladder ladder2 = new Ladder(24, 56);
        Ladder ladder3 = new Ladder(28, 45);

        builder.addLadder(ladder1);
        builder.addLadder(ladder2);
        builder.addLadder(ladder3);

        Snake snake1 = new Snake(48, 7);
        Snake snake2 = new Snake(63, 32);
        Snake snake3 = new Snake(84, 54);

        builder.addSnake(snake1);
        builder.addSnake(snake2);
        builder.addSnake(snake3);

        return builder.buildCongifuration();
    }

    @Test
    public void testIsSnakeBite() {
        // non ladder and non snake
        Assert.assertFalse(gameRule.isSnakeBite(new Cell(11)));
        // non ladder and non snake
        Assert.assertFalse(gameRule.isSnakeBite(new Cell(33)));
        // non ladder and non snake
        Assert.assertFalse(gameRule.isSnakeBite(new Cell(76)));

        // ladder cell
        Assert.assertFalse(gameRule.isSnakeBite(new Cell(5)));
        // ladder cell
        Assert.assertFalse(gameRule.isSnakeBite(new Cell(24)));
        // ladder cell
        Assert.assertFalse(gameRule.isSnakeBite(new Cell(28)));

        // snake cell
        Assert.assertTrue(gameRule.isSnakeBite(new Cell(48)));
        // snake cell
        Assert.assertTrue(gameRule.isSnakeBite(new Cell(63)));
        // snake cell
        Assert.assertTrue(gameRule.isSnakeBite(new Cell(84)));
    }

    @Test
    public void testIsLadderJump() {
        // non ladder and non snake
        Assert.assertFalse(gameRule.isLadderJump(new Cell(11)));
        // non ladder and non snake
        Assert.assertFalse(gameRule.isLadderJump(new Cell(33)));
        // non ladder and non snake
        Assert.assertFalse(gameRule.isLadderJump(new Cell(76)));

        // ladder cell
        Assert.assertTrue(gameRule.isLadderJump(new Cell(5)));
        // ladder cell
        Assert.assertTrue(gameRule.isLadderJump(new Cell(24)));
        // ladder cell
        Assert.assertTrue(gameRule.isLadderJump(new Cell(28)));

        // snake cell
        Assert.assertFalse(gameRule.isLadderJump(new Cell(48)));
        // snake cell
        Assert.assertFalse(gameRule.isLadderJump(new Cell(63)));
        // snake cell
        Assert.assertFalse(gameRule.isLadderJump(new Cell(84)));
    }

    @Test
    public void testGetNextMove() {
        // move from start
        Assert.assertEquals(gameRule.getNextMove(new Cell(0), 4).getNumber(), 4);
        // move without ladder and snake
        Assert.assertEquals(gameRule.getNextMove(new Cell(21), 5).getNumber(), 26);

        // move to take a ladder1
        Assert.assertEquals(gameRule.getNextMove(new Cell(1), 4).getNumber(), 20);
        // move to take a ladder1
        Assert.assertEquals(gameRule.getNextMove(new Cell(0), 5).getNumber(), 20);
        // move to take a ladder2
        Assert.assertEquals(gameRule.getNextMove(new Cell(20), 4).getNumber(), 56);
        // move to take a ladder3
        Assert.assertEquals(gameRule.getNextMove(new Cell(25), 3).getNumber(), 45);

        // move to get snake1
        Assert.assertEquals(gameRule.getNextMove(new Cell(45), 3).getNumber(), 7);
        // move to get snake2
        Assert.assertEquals(gameRule.getNextMove(new Cell(57), 6).getNumber(), 32);
        // move to get snake3
        Assert.assertEquals(gameRule.getNextMove(new Cell(83), 1).getNumber(), 54);

        //move to win a game
        Assert.assertEquals(gameRule.getNextMove(new Cell(98), 4).getNumber(), 98);
        //move to win a game
        Assert.assertEquals(gameRule.getNextMove(new Cell(99), 3).getNumber(), 99);
        //move to win a game
        Assert.assertEquals(gameRule.getNextMove(new Cell(95), 6).getNumber(), 95);
        //move to win a game
        Assert.assertEquals(gameRule.getNextMove(new Cell(99), 1).getNumber(), 100);
        //move to win a game
        Assert.assertEquals(gameRule.getNextMove(new Cell(97), 3).getNumber(), 100);
        //move to win a game
        Assert.assertEquals(gameRule.getNextMove(new Cell(95), 5).getNumber(), 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tesGetNextMoveInvalidDiceValue() {
        // invalid Dice value should throw IllegalArgumentException
        gameRule.getNextMove(new Cell(11), 8);

    }
}
