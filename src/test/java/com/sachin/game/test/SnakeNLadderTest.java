package com.sachin.game.test;

import com.sachin.game.api.GameBoard;
import com.sachin.game.api.GameController;
import com.sachin.game.api.Player;
import com.sachin.game.impl.GameBoardImpl;
import com.sachin.game.impl.GameControllerImpl;
import com.sachin.game.impl.PlayerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by C5203803 on 7/31/2014.
 */
public class SnakeNLadderTest {

    private GameBoardImpl.GameBoardBuilder initGame() {
        GameBoardImpl.GameBoardBuilder builder = new GameBoardImpl.GameBoardBuilder();
        builder.setColumns(10);
        builder.setRows(12);
        builder.setNoOfPlayers(3);

        builder.addLadder(4, 14);
        builder.addLadder(9, 31);
        builder.addLadder(20, 38);

        builder.addSnake(17, 7);
        builder.addSnake(54, 34);
        builder.addSnake(62, 19);

        return  builder;
    }

    @Test
    public void testGameBoard() {
        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder(initGame());
        GameBoard board1 = builder1.buildGame();

        assertEquals(10, board1.getColumns());
        assertEquals(12, board1.getRows());
        assertEquals(3, board1.getNoOfPlayers());
    }

    @Test
    public void testGameBoard_Ladders() {
        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder(initGame());
        GameBoard board1 = builder1.buildGame();

        assertNotNull(board1.getLadders());
        assertFalse(board1.getLadders().isEmpty());

        assertEquals(board1.getLadderForCell(4), 14);
        assertEquals(board1.getLadderForCell(9), 31);
        assertEquals(board1.getLadderForCell(20), 38);

        assertEquals(board1.getLadderForCell(5), -1);
        assertEquals(board1.getLadderForCell(45), -1);
    }

    @Test
    public void testGameBoard_Snakes() {

        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder(initGame());
        GameBoard board1 = builder1.buildGame();

        assertNotNull(board1.getSnakes());
        assertFalse(board1.getSnakes().isEmpty());

        assertEquals(board1.getSnakeForCell(17), 7);
        assertEquals(board1.getSnakeForCell(54), 34);
        assertEquals(board1.getSnakeForCell(62), 19);

        assertEquals(board1.getSnakeForCell(53), -1);
        assertEquals(board1.getSnakeForCell(73), -1);
    }

    /**
     * building a board with ladder on initial cell should throw an exception
     */
    @Test(expected = IllegalStateException.class)
    public void testGameBoard_LadderValidation1() {
        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder(initGame());
        builder1.addLadder(0, 100);

        GameBoard board1 = builder1.buildGame();
    }

    /**
     * * building a board with ladder on winning cell should throw an exception
     */
    @Test(expected = IllegalStateException.class)
    public void testGameBoard_LadderValidation2() {
        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder(initGame());
        builder1.addLadder(120, 120);

        GameBoard board1 = builder1.buildGame();
    }

    /**
     * building a board with snake on initial cell should throw an exception
     */
    @Test(expected = IllegalStateException.class)
    public void testGameBoard_SnakeValidation1() {
        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder(initGame());
        builder1.addSnake(0, 0);

        GameBoard board1 = builder1.buildGame();
    }

    /**
     * * building a board with snake on winning cell should throw an exception
     */
    @Test(expected = IllegalStateException.class)
    public void testGameBoard_SnakeValidation2() {
        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder(initGame());
        builder1.addSnake(120, 4);

        GameBoard board1 = builder1.buildGame();
    }

    /**
     * * building a board with snake and ladder at same cell should throw an exception
     */
/*    @Test(expected = IllegalStateException.class)
    public void testGameBoard_LadderSnakeAtSameCell(){
        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder(builder);
        builder1.addSnake(33, 17);
        builder1.addLadder(33, 74);

        GameBoard board1 = builder1.buildGame();
    }*/
    @Test
    public void testGameController() {
        GameBoard board = initGame().buildGame();
        GameController controller = new GameControllerImpl(board);
        assertEquals(controller.getGameBoard(), board);
        assertEquals(controller.getPlayers().size(), board.getNoOfPlayers());
    }

    @Test
    public void testRollDiceValidValue() {
        GameBoard board = initGame().buildGame();
        GameController controller = new GameControllerImpl(board);
        int dice = controller.rollDice();
        Assert.assertTrue(dice > 0 && dice < 7);
    }

    @Test
    public void testPlayer() {
        GameBoard board = initGame().buildGame();
        GameController controller = new GameControllerImpl(board);
        Player player = new PlayerImpl(controller);

        assertEquals("Default name should be Player1", player.getName(), "Player1");
        assertEquals("Initial position should be 0", player.getCurrentPosition(), 0);
        assertTrue("No move history present at start", player.getMoveHistory().isEmpty());
        assertFalse("Player winning flag should be fals", player.isPlayerWon());
    }

    @Test
    public void testPlayer_nameSet() {
        GameBoard board = initGame().buildGame();
        GameController controller = new GameControllerImpl(board);
        Player player = new PlayerImpl(controller);

        player.setName("Master");

        assertEquals("Master", player.getName());
    }


    @Test
    public void testPlayer_playMove() {
        GameBoard board = initGame().buildGame();
        GameController controller = new GameControllerImpl(board);
        Player player = new PlayerImpl(controller);

        player.playMove(3);

        assertEquals("CurrentPosition should be 3", player.getCurrentPosition(), 3);
        assertFalse("MOve history should not be empty", player.getMoveHistory().isEmpty());
        assertNotNull("Last move cannot be null", player.getMoveHistory().get(0));
        assertEquals("Last move should be f(0,3)=3 ", "f(0,3)=3", player.getMoveHistory().get(0));
    }


    @Test
    public void testPlayer_playMove_winWithLadders() {

        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(3);

        builder1.addLadder(4, 14);
        builder1.addLadder(9, 31);
        builder1.addLadder(20, 38);
        builder1.addLadder(42, 84);
        builder1.addLadder(87, 97);

        GameBoard board1 = builder1.buildGame();
        GameController controller1 = new GameControllerImpl(board1);

        Player player1 = new PlayerImpl(controller1);
        player1.playMove(3);
        player1.playMove(1);
        player1.playMove(6);
        player1.playMove(4);
        player1.playMove(3);
        player1.playMove(3);

        assertEquals("CurrentPosition should be 100", player1.getCurrentPosition(), 100);
        assertFalse("Move history should not be empty", player1.getMoveHistory().isEmpty());
        assertEquals("Total moves should be 6", player1.getMoveHistory().size(), 6);
        assertEquals("First move should be f(0,3)=3 ", "f(0,3)=3", player1.getMoveHistory().get(0));
        assertEquals("Last move should be f(97,3)=100 ", "f(97,3)=100", player1.getMoveHistory().get(5));
        assertEquals("Player is winner", player1.isPlayerWon(), true);
    }

    @Test
    public void testPlayer_playMove_unsuccessfulPlayWithLadders() {

        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(3);

        builder1.addLadder(4, 14);
        builder1.addLadder(9, 31);
        builder1.addLadder(20, 85);
        builder1.addLadder(87, 97);

        GameBoard board1 = builder1.buildGame();
        GameController controller1 = new GameControllerImpl(board1);

        Player player1 = new PlayerImpl(controller1);
        player1.playMove(4);
        player1.playMove(6);
        player1.playMove(2);
        player1.playMove(4);

        assertEquals("CurrentPosition should be 97", player1.getCurrentPosition(), 97);
        assertFalse("MOve history should not be empty", player1.getMoveHistory().isEmpty());
        assertEquals("Total moves should be 4", player1.getMoveHistory().size(), 4);
        assertEquals("First move should be f(0,4)=14 ", "f(0,4)=14", player1.getMoveHistory().get(0));
        assertEquals("Last move should be f(97,4)=97 ", "f(97,4)=97", player1.getMoveHistory().get(3));
        assertEquals("Player is winner", player1.isPlayerWon(), false);
    }

    @Test
    public void testPlayer_playMove_unsuccessfulPlayWithSnakes() {

        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(3);

        builder1.addLadder(4, 14);
        builder1.addLadder(9, 16);
        builder1.addLadder(20, 85);

        builder1.addSnake(17, 7);

        GameBoard board1 = builder1.buildGame();
        GameController controller1 = new GameControllerImpl(board1);

        Player player1 = new PlayerImpl(controller1);
        player1.playMove(4);
        player1.playMove(3);
        player1.playMove(2);
        player1.playMove(4);
        player1.playMove(5);

        assertEquals("CurrentPosition should be 90", player1.getCurrentPosition(), 90);
        assertFalse("MOve history should not be empty", player1.getMoveHistory().isEmpty());
        assertEquals("Total moves should be 5", player1.getMoveHistory().size(), 5);
        assertEquals("First move should be f(0,4)=14 ", "f(0,4)=14", player1.getMoveHistory().get(0));
        assertEquals("Last move should be f(85,5)=90 ", "f(85,5)=90", player1.getMoveHistory().get(4));
        assertEquals("Player is winner", player1.isPlayerWon(), false);
    }

    @Test
    public void testPlayer_playMove_multiPlayer() {

        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(3);

        builder1.addLadder(4, 14);
        builder1.addLadder(9, 16);
        builder1.addLadder(20, 85);

        builder1.addSnake(17, 7);

        GameBoard board1 = builder1.buildGame();
        GameController controller1 = new GameControllerImpl(board1);

        Player player1 = new PlayerImpl(controller1);
        Player player2 = new PlayerImpl(controller1);

        player1.playMove(5);
        player2.playMove(3);

        assertEquals("CurrentPosition should be 5", player1.getCurrentPosition(), 5);
        assertEquals("CurrentPosition should be 3", player2.getCurrentPosition(), 3);

        player1.playMove(2);
        player2.playMove(6);

        assertEquals("CurrentPosition should be 7", player1.getCurrentPosition(), 7);
        assertEquals("CurrentPosition should be 16", player2.getCurrentPosition(), 16);

        player1.playMove(5);
        player2.playMove(5);

        assertEquals("CurrentPosition should be 12", player1.getCurrentPosition(), 12);
        assertEquals("CurrentPosition should be 21", player2.getCurrentPosition(), 21);

        player1.playMove(5);
        player2.playMove(1);

        assertEquals("CurrentPosition should be 7", player1.getCurrentPosition(), 7);
        assertEquals("CurrentPosition should be 22", player2.getCurrentPosition(), 22);

        player1.playMove(5);
        player2.playMove(3);

        assertEquals("CurrentPosition should be 12", player1.getCurrentPosition(), 12);
        assertEquals("CurrentPosition should be 25", player2.getCurrentPosition(), 25);

        assertFalse("MOve history should not be empty", player1.getMoveHistory().isEmpty());
        assertEquals("Total moves should be 5", player1.getMoveHistory().size(), 5);
        assertEquals("First move should be f(0,5)=5 ", "f(0,5)=5", player1.getMoveHistory().get(0));
        assertEquals("Last move should be f(7,5)=12 ", "f(7,5)=12", player1.getMoveHistory().get(4));
        assertEquals("Player is winner", player1.isPlayerWon(), false);

        assertFalse("MOve history should not be empty", player2.getMoveHistory().isEmpty());
        assertEquals("Total moves should be 5", player2.getMoveHistory().size(), 5);
        assertEquals("First move should be f(0,3)=3 ", "f(0,3)=3", player2.getMoveHistory().get(0));
        assertEquals("Last move should be f(22,3)=25 ", "f(22,3)=25", player2.getMoveHistory().get(4));
        assertEquals("Player is winner", player2.isPlayerWon(), false);

    }

    @Test
    public void testPlayer_playMove_multiPlayer_Player2Wins() {

        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(3);

        builder1.addLadder(4, 14);
        builder1.addLadder(9, 16);
        builder1.addLadder(20, 95);

        builder1.addSnake(17, 7);

        GameBoard board1 = builder1.buildGame();
        GameController controller1 = new GameControllerImpl(board1);

        Player player1 = new PlayerImpl(controller1);
        Player player2 = new PlayerImpl(controller1);

        player1.playMove(5);
        player2.playMove(3);

        assertEquals("CurrentPosition should be 5", player1.getCurrentPosition(), 5);
        assertEquals("CurrentPosition should be 3", player2.getCurrentPosition(), 3);

        player1.playMove(2);
        player2.playMove(6);

        assertEquals("CurrentPosition should be 7", player1.getCurrentPosition(), 7);
        assertEquals("CurrentPosition should be 16", player2.getCurrentPosition(), 16);

        player1.playMove(5);
        player2.playMove(4);

        assertEquals("CurrentPosition should be 12", player1.getCurrentPosition(), 12);
        assertEquals("CurrentPosition should be 95", player2.getCurrentPosition(), 95);

        player1.playMove(5);
        player2.playMove(3);

        assertEquals("CurrentPosition should be 7", player1.getCurrentPosition(), 7);
        assertEquals("CurrentPosition should be 98", player2.getCurrentPosition(), 98);

        player1.playMove(5);
        player2.playMove(2);

        assertEquals("CurrentPosition should be 12", player1.getCurrentPosition(), 12);
        assertEquals("CurrentPosition should be 25", player2.getCurrentPosition(), 100);

        assertFalse("MOve history should not be empty", player1.getMoveHistory().isEmpty());
        assertEquals("Total moves should be 5", player1.getMoveHistory().size(), 5);
        assertEquals("First move should be f(0,5)=5 ", "f(0,5)=5", player1.getMoveHistory().get(0));
        assertEquals("Last move should be f(7,5)=12 ", "f(7,5)=12", player1.getMoveHistory().get(4));
        assertEquals("Player is winner", player1.isPlayerWon(), false);

        assertFalse("MOve history should not be empty", player2.getMoveHistory().isEmpty());
        assertEquals("Total moves should be 5", player2.getMoveHistory().size(), 5);
        assertEquals("First move should be f(0,3)=3 ", "f(0,3)=3", player2.getMoveHistory().get(0));
        assertEquals("Last move should be f(98,2)=100 ", "f(98,2)=100", player2.getMoveHistory().get(4));
        assertEquals("Player is winner", player2.isPlayerWon(), true);

    }

    @Test
    public void testPlayer_winWithExactNumber(){
        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(3);

        builder1.addLadder(4, 14);
        builder1.addLadder(15, 97);

        GameBoard board1 = builder1.buildGame();
        GameController controller1 = new GameControllerImpl(board1);

        Player player1 = new PlayerImpl(controller1);

        player1.playMove(4);
        player1.playMove(1);
        player1.playMove(6);
        assertEquals("CurrentPosition should be 97", player1.getCurrentPosition(), 97);

        player1.playMove(4);
        assertEquals("CurrentPosition should be 97", player1.getCurrentPosition(), 97);

        player1.playMove(3);
        assertEquals("CurrentPosition should be 100", player1.getCurrentPosition(), 100);
        assertTrue("Player1 wins the game.", player1.isPlayerWon());

        assertFalse("MOve history should not be empty", player1.getMoveHistory().isEmpty());
        assertEquals("Total moves should be 5", player1.getMoveHistory().size(), 5);
        assertEquals("third LAst move should be f(97,6)=97 ", "f(97,6)=97", player1.getMoveHistory().get(2));
        assertEquals("Second Last move should be f(97,4)=97 ", "f(97,4)=97", player1.getMoveHistory().get(3));
        assertEquals("Last move should be f(97,3)=100 ", "f(97,3)=100", player1.getMoveHistory().get(4));


    }

    @Test
    public void testPlayer_undoMove(){
        GameBoardImpl.GameBoardBuilder builder1 = new GameBoardImpl.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(3);

        builder1.addLadder(4, 14);
        builder1.addLadder(9, 16);
        builder1.addLadder(20, 95);

        builder1.addSnake(17, 7);

        GameBoard board1 = builder1.buildGame();
        GameController controller1 = new GameControllerImpl(board1);

        Player player1 = new PlayerImpl(controller1);

        player1.playMove(4);

        assertEquals("CurrentPosition should be 14", player1.getCurrentPosition(), 14);
        assertFalse("MOve history should not be empty", player1.getMoveHistory().isEmpty());
        assertEquals("Total moves should be 1", player1.getMoveHistory().size(), 1);
        assertEquals("Last move should be f(0,4)=14 ", "f(0,4)=14", player1.getMoveHistory().get(0));

        player1.undoMove();

        assertEquals("CurrentPosition should be 0", player1.getCurrentPosition(), 0);
        assertTrue("Move history should be empty", player1.getMoveHistory().isEmpty());
        assertEquals("Total moves should be 0", player1.getMoveHistory().size(), 0);
    }
}
