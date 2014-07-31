package com.sachin.game.api.test;

import com.sachin.game.api.GameController;
import com.sachin.game.api.Player;
import com.sachin.game.api.GameBoard;
import com.sachin.game.impl.GameBoardImpl;
import com.sachin.game.impl.GameControllerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by C5203803 on 7/24/2014.
 */
public class GameControllerTest {

    private GameBoardImpl gameBoard;

    private GameController controller;

    @Before
    public void setup(){
        gameBoard = initGameConfiguration();
        controller = new GameControllerImpl(gameBoard);
    }

    @Test
    public void testGetGameConfiguration(){

        GameBoard conf = controller.getGameBoard();

        Assert.assertEquals(conf, gameBoard);
    }

    @Test
    public void testGetPlayers(){
        List<Player> players = controller.getPlayers();

        Assert.assertNotNull(players);
        // no of players should match gameBoard
        Assert.assertEquals(players.size(), gameBoard.getNoOfPlayers());

        //All player should have current position 0 and no game history
        for(Player player : players){
            Assert.assertNotNull(player.getName());
            Assert.assertEquals(player.getCurrentPosition(), 0);
            Assert.assertEquals(player.getMoveHistory().size(),0);
            Assert.assertEquals(player.isPlayerWon(), false);
        }
    }

    @Test
    public void testGetRollDice(){
        int dice = controller.rollDice();

        Assert.assertTrue(dice > 0 && dice < 7);
    }

    @Test
    public void testResetPlayers(){
        controller.resetPlayers();

        List<Player> players = controller.getPlayers();

        Assert.assertNotNull(players);
        // no of players should match gameBoard
        Assert.assertEquals(players.size(), gameBoard.getNoOfPlayers());

        //All player should have current position 0 and no game history
        for(Player player : players){
            Assert.assertNotNull(player.getName());
            Assert.assertEquals(player.getCurrentPosition(), 0);
            Assert.assertEquals(player.getMoveHistory().size(),0);
            Assert.assertEquals(player.isPlayerWon(), false);
        }
    }
    private GameBoardImpl initGameConfiguration() {

        GameBoardImpl.GameConfigurationBuilder builder = new GameBoardImpl.GameConfigurationBuilder();
        builder.setColumns(10);
        builder.setRows(10);
        builder.setNoOfPlayers(2);

        builder.addLadder(5, 20);
        builder.addLadder(24, 56);
        builder.addLadder(28, 45);

        builder.addSnake(48, 7);
        builder.addSnake(63, 32);
        builder.addSnake(84, 54);

        return builder.buildCongifuration();
    }

    @Test(expected = IllegalArgumentException.class)
    public void tesGetNextMoveInvalidDiceValue() {
        // invalid Dice value should throw IllegalArgumentException
        controller.getNextMove(11, 8);

    }

    @Test
    public void testIsSnakeBite() {
        // non ladder and non snake
        Assert.assertFalse(gameBoard.isSnakeBite(11));
        // non ladder and non snake
        Assert.assertFalse(gameBoard.isSnakeBite(33));
        // non ladder and non snake
        Assert.assertFalse(gameBoard.isSnakeBite(76));

        // ladder cell
        Assert.assertFalse(gameBoard.isSnakeBite(5));
        // ladder cell
        Assert.assertFalse(gameBoard.isSnakeBite(24));
        // ladder cell
        Assert.assertFalse(gameBoard.isSnakeBite(28));

        // snake cell
        Assert.assertTrue(gameBoard.isSnakeBite(48));
        // snake cell
        Assert.assertTrue(gameBoard.isSnakeBite(63));
        // snake cell
        Assert.assertTrue(gameBoard.isSnakeBite(84));
    }

    @Test
    public void testIsLadderJump() {
        // non ladder and non snake
        Assert.assertFalse(gameBoard.isLadderJump(11));
        // non ladder and non snake
        Assert.assertFalse(gameBoard.isLadderJump(33));
        // non ladder and non snake
        Assert.assertFalse(gameBoard.isLadderJump(76));

        // ladder cell
        Assert.assertTrue(gameBoard.isLadderJump(5));
        // ladder cell
        Assert.assertTrue(gameBoard.isLadderJump(24));
        // ladder cell
        Assert.assertTrue(gameBoard.isLadderJump(28));

        // snake cell
        Assert.assertFalse(gameBoard.isLadderJump(48));
        // snake cell
        Assert.assertFalse(gameBoard.isLadderJump(63));
        // snake cell
        Assert.assertFalse(gameBoard.isLadderJump(84));
    }

    @Test
    public void testGetNextMove() {
        // move from start
        Assert.assertEquals(controller.getNextMove(0, 4), 4);
        // move without ladder and snake
        Assert.assertEquals(controller.getNextMove(21, 5), 26);

        // move to take a ladder1
        Assert.assertEquals(controller.getNextMove(1, 4), 20);
        // move to take a ladder1
        Assert.assertEquals(controller.getNextMove(0, 5), 20);
        // move to take a ladder2
        Assert.assertEquals(controller.getNextMove(20, 4), 56);
        // move to take a ladder3
        Assert.assertEquals(controller.getNextMove(25, 3), 45);

        // move to get snake1
        Assert.assertEquals(controller.getNextMove(45, 3), 7);
        // move to get snake2
        Assert.assertEquals(controller.getNextMove(57, 6), 32);
        // move to get snake3
        Assert.assertEquals(controller.getNextMove(83, 1), 54);

        //move to win a game
        Assert.assertEquals(controller.getNextMove(98, 4), 98);
        //move to win a game
        Assert.assertEquals(controller.getNextMove(99, 3), 99);
        //move to win a game
        Assert.assertEquals(controller.getNextMove(95, 6), 95);
        //move to win a game
        Assert.assertEquals(controller.getNextMove(99, 1), 100);
        //move to win a game
        Assert.assertEquals(controller.getNextMove(97, 3), 100);
        //move to win a game
        Assert.assertEquals(controller.getNextMove(95, 5), 100);
    }

    @Test
    public void testRollDiceValidValue() {
        int dice = controller.rollDice();
        Assert.assertTrue(dice > 0 && dice < 7);
    }

    @Test
    public void testRollDiceRandomness() {
        List<Integer> diceValArray = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            int dice = controller.rollDice();
//            System.out.println("Dice = " + dice);
            diceValArray.add(dice);
        }
        // frequency of any number should not be 4 or more than that in consecutive 6 attempts
        Assert.assertFalse("frequency of any number should not be 4 or more than that in consecutive 6 attempts", Collections.frequency(diceValArray, Integer.valueOf(1)) >= 4);
        Assert.assertFalse("frequency of any number should not be 4 or more than that in consecutive 6 attempts", Collections.frequency(diceValArray, Integer.valueOf(2)) >= 4);
        Assert.assertFalse("frequency of any number should not be 4 or more than that in consecutive 6 attempts", Collections.frequency(diceValArray, Integer.valueOf(3)) >= 4);
        Assert.assertFalse("frequency of any number should not be 4 or more than that in consecutive 6 attempts", Collections.frequency(diceValArray, Integer.valueOf(4)) >= 4);
        Assert.assertFalse("frequency of any number should not be 4 or more than that in consecutive 6 attempts", Collections.frequency(diceValArray, Integer.valueOf(5)) >= 4);
        Assert.assertFalse("frequency of any number should not be 4 or more than that in consecutive 6 attempts", Collections.frequency(diceValArray, Integer.valueOf(6)) >= 4);
    }
}
